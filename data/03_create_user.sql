-- Database: userService

CREATE DATABASE "userService"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

\c "userService"


-- Table: public.user_credential

CREATE TABLE IF NOT EXISTS public.user_credential
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    role character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    username character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_user_id PRIMARY KEY (id),
    CONSTRAINT uk_username UNIQUE (username)
        INCLUDE(username)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.user_credential
    OWNER to postgres;


-- Table: public.customer

CREATE TABLE IF NOT EXISTS public.customer
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_id integer NOT NULL,
    phone character varying COLLATE pg_catalog."default" NOT NULL,
    tariff_id integer NOT NULL,
    CONSTRAINT pk_customer_id PRIMARY KEY (id),
    CONSTRAINT u_phone_number UNIQUE (phone),
    CONSTRAINT fk_customer_user_id FOREIGN KEY (user_id)
        REFERENCES public.user_credential (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.customer
    OWNER to postgres;
