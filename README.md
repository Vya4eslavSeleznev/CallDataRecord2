# Стек

Использовались следующие технологии:

- Java 11
- Spring Boot
- Spring Mvc
- Spring Security
- Spring Data JPA
- Swagger 3
- Junit 5
- Docker
- Lombok
- PostgreSQL
- ActiveMQ

# Микросервисы

 - При разработке было принято решение создать 6 микросервисов:
    - `user`
    - `tariff`
    - `brt`
    - `hrs`
    - `cdr`
    - `crm`

- Микросервис `user` необходим для работы с пользователями: клиенты и менеджеры. Взаимодействует с собственной
базой данных `userService`

- Микросервис `tariff` необходим для работы с тарифами. Взаимодействует с собственной базой данных `tariffService`.
Реализация сервиса позволяет добавить любой тариф, что удовлетворяет дополнительному заданию

- Микросервис `brt (Billing Real Time)` необходим для работы с клиентами оператора "Ромашка", имеющих положительный
баланс. Взаимодействует с собственной базой данных `brt`. Сервис позволяет обрабатывать звонки, поступающие с `cdr`,
управлять балансом пользователей и следить за транзакциями

- Микросервис `hrs (High perfomance rating server)` необходим для расчета звонков по конкретному тарифу

- Микросервис `cdr (Call Data Record)` позволяет генерировать звонки на основе `.txt` файла, тем самым запсускать
тарификацию.При первом запуске тарификация производится автоматически, после этого есть возможность воспользоваться
эндпоинтом. Пример записи звонка `INPUT, 73734435243, 20230725141448, 20230725142110`:
    - На первом месте стоит тип звонка: входящий `INPUT` / исходящий `OUTPUT`
    - Затем телефона пользователя
    - После две даты: начало и конец звонка

- Микросервис `crm` необходим для управления всеми микросервисами с использованием двух ролей: `MANAGER` и `USER`

# Как микросервисы работают между собой?

![Schema](docs/schema.png)

- Вся работа системы начинается с микросервиса `cdr`, задача которого сгенерировать событие `CallRecordEvent`, которое
содержит в себе информацию:

```json
{
  "callType": "INPUT",
  "phoneNumber": "73734435243",
  "startDate": "20230725141448",
  "endDate": "20230725142110"
}
```

- Слушателем данного события является `brt`, задача которого на основе данных отправить запрос в микросервис `user` и
получить всю инофрмацию о клиенте. После этого `brt` проверяет баланс пользователя, если он отрицательный, то
выбросится исключение `BalanceLessThanZeroException`, в противном случае сгенерируется событие `CallAuthorizedEvent`,
которое содержит в себе информацию:

```json
  "callType": "INPUT",
  "accountId": 1,
  "tariffId": 1,
  "startDate" "2023-07-25T11:14:48.000+00:00",
  "endDate": "2023-07-25T11:21:10.000+00:00",
  "minutesSpent": 298
```

Где параметр `minutesSpent` обозначает количество минут потраченных в текущем месяце. Месяц определяется на основании
реальной даты

- Затем в дело вступает микросервис `hrs`, который является слушателем `brt`. На основании `tariffId` производится
запрос в микросервис `tariff`, откуда достается вся нужная информация по тарифам. На основе данных производится
расчет стоимости звонка и генерируется событие `CallCostCalculatedEvent`:

```json
  "accountId": 1,
  "callType": "INPUT",
  "cost": 76,
  "startDate" "2023-07-25T11:14:48.000+00:00",
  "endDate": "2023-07-25T11:21:10.000+00:00",
  "duration": "00:06:22.000+00:00"
```

- Событие от `hrs` принимает `brt`, проверяет баланс пользователя и вносит изменения по балансу в `account` и
добавляет звонок в таблицу `account_call`

- Задача микросервиса `crm` заключается в рассылке запросов в нужные микросервисы

# Описание баз данных

- В проекте реализованы три базы данных для соответствующих микросервисов:
    - `brt`
    - `tariff`
    - `user`

### База данных для `brt`

![BrtDB](docs/brt_db.png)

- База данных содержит в себе три таблицы:
    - `account` - аккаунт пользователя, где хранится и обновляется его баланс. У пользователя может быть только один
    аккаунт
    - `account_call` - таблица со звонками пользователей, данные попадают туда после того, как произвошла
    тарификация. За каждый звонок списываются деньги с баланса - таблица `account`
    - `account_call` - таблица с транзакциями пользователей. Хранит в себе информацию о поступлении денежных средств на
    баланс пользователя. При каждом добавлении транзакции значение баланса в таблице `account` обновляется

### База данных для `tariff`

![TariffDB](docs/tariff_db.png)

- База данных содержит в себе четыре таблицы:
    - `tariff` - таблица для хранения названий тарифов
    - `currency` - таблица для хранения полного и сокращенного названий валюты
    - `tariff_call_type` - таблица для определения типов звонков для каждого тарифа
    - `tariff_call_type_cost` - таблица для условий стоимости и длительности по используемому тарифу и после того, как
    тариф использовали для каждого типа звонка: входящий / исходящий

- Данная схема позволит создать любой тариф для оператора связи "Ромашка"

### База данных для `user`

![UserDB](docs/user_db.png)

- База данных содержит в себе две таблицы:
    - `user_credential` - хранит в себе секреты пользователя: роль, имя пользователя и его пароль, который хранится в
    неявном виде - в виде хэша
    - `customer` - таблица для хранения данных о клиентах

- При добавлении пользователей кроме записи в таблицы `user_credential` и `customer` добавляется запись в базу данных
`brt` - таблица `account` 

### Данные в БД

- При запуске приложения уже будут доступны клиенты и менеджер со следующими персональными данными:

|   Роль   |   Имя    |  Пароль  |
|----------|----------|----------|
|   USER   |    q     |   pwd    |
|   USER   |    w     |   pwd    |
|   USER   |    e     |   pwd    |
|   USER   |    r     |   pwd    |
|   USER   |    t     |   pwd    |
|   USER   |    y     |   pwd    |
|  MANAGER | manager  |   pwd    |

- И три тарифа:

| Название | tariff_if|
|----------|----------|
|Unlimited |    1     |
|PerMinute |    2     |
| Custom   |    3     |

- У тарифа `Unlimited` входящие и исходящие `300 min` за `100 rub` после `1 min` за `1.5 rub`
- У тарифа `PerMinute` входящие и исходящие по тарифу `500 eur` за `250 min` после входящие `1 min` за `3 eur`, а
исходящие `1 min` за `1 eur`
- У тарифа `Custom` входящие `1 usd` за `0.5 min`, а исходящие `1 min` за `5 usd`

# Описание эндпоинтов

### `http://localhost:8082/crm/signin`

- Для использование приложения необходимо авторизироваться

- Body:

```json
{
  "username": "q",
  "password": "pwd"
}
```

- Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE2ODI5NzM5MDMsImV4cCI6MTY4Mjk4NDcwM30.BcHmNWgtaiN0F7Ry0puxchjPGWy2i5VT-Lp7dMb2quc"
}
```

### `http://localhost:8082/crm/abonent/pay/`

- Пополнение баланса пользователя

- Body:

```json
{
  "phoneNumber": "73734435243",
  "amount": 5000
}
```

- Response:

```json
{
  "id": 2,
  "phoneNumber": "73734435243",
  "money": 5000
}
```

### `http://localhost:8082/crm/abonent/report/{phoneNumber}`

- Получение отчета по звонкам для пользователя по номеру телефона

- Response:

```json
{
  "id": 1,
  "phoneNumber": "73734435243",
  "tariffIndex": 2,
  "payload": [
    {
      "callType": "INPUT",
      "startTime": "2023-07-25T14:14:48.000+00:00",
      "endTime": "2023-07-25T14:21:10.000+00:00",
      "duration": "PT6M22S",
      "cost": 2.122222
    }
  ],
  "totalCost": 2.122222,
  "monetaryUnit": "Euro"
}
```

### `http://localhost:8082/crm/manager/billing`

- Выполнение тарификации для всех пользователей

- Response:

```json
{
  "numbers": [
    {
      "phoneNumber": "73160252296",
      "balance": 6991.333333
    },
    {
      "phoneNumber": "71911814507",
      "balance": 994.216667
    },
    {
      "phoneNumber": "75247690175",
      "balance": 996.844444
    },
    {
      "phoneNumber": "71768318906",
      "balance": 694.475
    },
    {
      "phoneNumber": "75211122285",
      "balance": 670.5
    },
    {
      "phoneNumber": "999",
      "balance": 999
    },
    {
      "phoneNumber": "73734435243",
      "balance": 12497.877778
    }
  ]
}
```

### `http://localhost:8082/crm/manager/abonent`

- Добавление нового пользователя

- Body:

```json
{
  "phoneNumber": "89990000011",
  "password": "pwd",
  "username": "nexign",
  "tariffId": 1,
  "balance": 11
}
```

- Response:

```json
{
  "phoneNumber": "89990000011",
  "tariffId": 1,
  "balance": 11
}
```

### `http://localhost:8082/crm/manager/changeTariff`

- Изменение тарифа у пользователя

- Body:

```json
{
  "phoneNumber": "73734435243",
  "tariffId": 1
}
```

- Response:

```json
{
  "id": 1,
  "phoneNumber": "73734435243",
  "tariffId": 1
}
```

### `http://localhost:8082/crm/manager/profile`

- Добавление менеджера в систему

- Body:

```json
{
    "password": "pwd",
    "username": "nexign_manager",
    "role": "MANAGER"
}
```

### Swagger

- для запуска Swagger необходимо воспользоваться url:

```
http://localhost:8082/swagger-ui/index.html#/
```

# Как запустить?

- Предварительно мной были созданы все образы микросервисов и добавлены на [DockerHub](https://hub.docker.com/u/vya4eslava):

- Для запуска Вам необходимо клонировать на свой компьютер репозиторий. Либо скачать .zip архив с проектом. После этого
выполнить команду:

```shell script
docker compose up -d
```
