-- Database: tariffService

CREATE DATABASE "tariffService"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

\c "tariffService"


-- Table: public.currency

CREATE TABLE IF NOT EXISTS public.currency
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    short_name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT currency_pkey PRIMARY KEY (id),
    CONSTRAINT name UNIQUE (name)
        INCLUDE(name),
    CONSTRAINT short_name UNIQUE (short_name)
        INCLUDE(short_name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.currency
    OWNER to postgres;


-- Table: public.tariff

CREATE TABLE IF NOT EXISTS public.tariff
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tariff_pkey PRIMARY KEY (id),
    CONSTRAINT tariff_unique_name UNIQUE (name)
        INCLUDE(name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tariff
    OWNER to postgres;


-- Table: public.tariff_call_type

CREATE TABLE IF NOT EXISTS public.tariff_call_type
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    tariff_id integer NOT NULL,
    call_type character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_tariff_call_type_id PRIMARY KEY (id),
    CONSTRAINT fk_tariff_id FOREIGN KEY (tariff_id)
        REFERENCES public.tariff (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tariff_call_type
    OWNER to postgres;


-- Table: public.tariff_call_type_cost

CREATE TABLE IF NOT EXISTS public.tariff_call_type_cost
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    tariff_call_type_id integer NOT NULL,
    tariffication_interval integer NOT NULL,
    price numeric NOT NULL,
    currency_id integer NOT NULL,
    type character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tariff_call_type_cost_pkey PRIMARY KEY (id),
    CONSTRAINT fk_currency_id FOREIGN KEY (currency_id)
        REFERENCES public.currency (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_tariff_call_type_id FOREIGN KEY (tariff_call_type_id)
        REFERENCES public.tariff_call_type (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tariff_call_type_cost
    OWNER to postgres;
