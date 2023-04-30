\c "tariffService"

INSERT INTO public.currency(
	name, short_name)
	VALUES ('Ruble', 'RUB');

INSERT INTO public.currency(
	name, short_name)
	VALUES ('Euro', 'EUR');

INSERT INTO public.currency(
	name, short_name)
	VALUES ('Dollar', 'USD');





INSERT INTO public.tariff(
	name)
	VALUES ('Unlimited');

INSERT INTO public.tariff(
	name)
	VALUES ('PerMinute');

INSERT INTO public.tariff(
	name)
	VALUES ('Custom');





INSERT INTO public.tariff_call_type(
	tariff_id, call_type)
	VALUES (1, 'INPUT');

INSERT INTO public.tariff_call_type(
	tariff_id, call_type)
	VALUES (1, 'OUTPUT');


INSERT INTO public.tariff_call_type(
	tariff_id, call_type)
	VALUES (2, 'INPUT');

INSERT INTO public.tariff_call_type(
	tariff_id, call_type)
	VALUES (2, 'OUTPUT');


INSERT INTO public.tariff_call_type(
	tariff_id, call_type)
	VALUES (3, 'INPUT');

INSERT INTO public.tariff_call_type(
	tariff_id, call_type)
	VALUES (3, 'OUTPUT');





INSERT INTO public.tariff_call_type_cost(
	tariff_call_type_id, tariffication_interval, price, currency_id, type)
	VALUES (1, 300, 100, 1, 'PREPAID');

INSERT INTO public.tariff_call_type_cost(
	tariff_call_type_id, tariffication_interval, price, currency_id, type)
	VALUES (2, 300, 100, 1, 'PREPAID');

INSERT INTO public.tariff_call_type_cost(
	tariff_call_type_id, tariffication_interval, price, currency_id, type)
	VALUES (1, 1, 1.5, 1, 'POSTPAID');

INSERT INTO public.tariff_call_type_cost(
	tariff_call_type_id, tariffication_interval, price, currency_id, type)
	VALUES (2, 1, 1.5, 1, 'POSTPAID');



INSERT INTO public.tariff_call_type_cost(
	tariff_call_type_id, tariffication_interval, price, currency_id, type)
	VALUES (3, 500, 250, 2, 'PREPAID');

INSERT INTO public.tariff_call_type_cost(
	tariff_call_type_id, tariffication_interval, price, currency_id, type)
	VALUES (3, 1, 3, 2, 'POSTPAID');

INSERT INTO public.tariff_call_type_cost(
	tariff_call_type_id, tariffication_interval, price, currency_id, type)
	VALUES (4, 1, 1, 2, 'POSTPAID');



INSERT INTO public.tariff_call_type_cost(
	tariff_call_type_id, tariffication_interval, price, currency_id, type)
	VALUES (5, 1, 0.5, 3, 'POSTPAID');

INSERT INTO public.tariff_call_type_cost(
	tariff_call_type_id, tariffication_interval, price, currency_id, type)
	VALUES (6, 1, 5, 3, 'POSTPAID');
