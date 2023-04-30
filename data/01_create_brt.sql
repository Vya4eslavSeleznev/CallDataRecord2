-- Database: brt

CREATE DATABASE brt
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

\c "brt"


-- Table: public.account

CREATE TABLE IF NOT EXISTS public.account
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_id bigint NOT NULL,
    balance numeric(15,6) NOT NULL,
    CONSTRAINT "Account_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.account
    OWNER to postgres;


-- Table: public.account_call

CREATE TABLE IF NOT EXISTS public.account_call
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    account_id integer NOT NULL,
    call_type character varying COLLATE pg_catalog."default" NOT NULL,
    start_date timestamp without time zone NOT NULL,
    end_date timestamp without time zone NOT NULL,
    duration interval NOT NULL,
    cost numeric(15,6) NOT NULL,
    CONSTRAINT pk_account_call_id PRIMARY KEY (id),
    CONSTRAINT fk_account_id FOREIGN KEY (account_id)
        REFERENCES public.account (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.account_call
    OWNER to postgres;


-- Table: public.account_transaction

CREATE TABLE IF NOT EXISTS public.account_transaction
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    account_id integer NOT NULL,
    amount numeric(15,6) NOT NULL,
    CONSTRAINT pk_account_transaction_id PRIMARY KEY (id)
        INCLUDE(id),
    CONSTRAINT fk_account_id FOREIGN KEY (account_id)
        REFERENCES public.account (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.account_transaction
    OWNER to postgres;
