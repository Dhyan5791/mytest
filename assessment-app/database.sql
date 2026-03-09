CREATE DATABASE assessment
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE public.roles
(
    role_id serial NOT NULL,
    role_name character varying(255) NOT NULL,
    PRIMARY KEY (role_id),
    CONSTRAINT uq_roles_role_name UNIQUE (role_name)
);

ALTER TABLE IF EXISTS public.roles
    OWNER to postgres;

CREATE TABLE public.users
(
    user_id serial NOT NULL,
    username character varying(50) NOT NULL,
    email character varying(100) NOT NULL,
    password text NOT NULL,
    is_active boolean NOT NULL DEFAULT true,
    created_at timestamp without time zone NOT NULL DEFAULT now(),
    updated_at timestamp without time zone,
    PRIMARY KEY (user_id),
    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT uq_users_email UNIQUE (email)
);

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;


CREATE TABLE public.user_roles
(
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    FOREIGN KEY (role_id) REFERENCES public.roles(role_id)
);

ALTER TABLE IF EXISTS public.user_roles
    OWNER to postgres;


CREATE TABLE public.products
(
    product_id serial,
    product_name character varying(255) NOT NULL,
    price numeric(18, 2) NOT NULL CHECK (price >= 0),
    PRIMARY KEY (product_id)
);

ALTER TABLE IF EXISTS public.products
    OWNER to postgres;


CREATE TABLE public.taxes
(
    tax_id serial NOT NULL,
    tax_name character varying(255) NOT NULL,
    rate numeric(5, 2) NOT NULL CHECK (rate >= 0 AND rate <= 100),
    PRIMARY KEY (tax_id),
    CONSTRAINT uq_taxes_tax_name UNIQUE (tax_name)
);

ALTER TABLE IF EXISTS public.taxes
    OWNER to postgres;

CREATE TABLE public.product_taxes
(
    product_id integer NOT NULL,
    tax_id integer NOT NULL,
    PRIMARY KEY (product_id, tax_id),
    FOREIGN KEY (product_id) REFERENCES public.products(product_id),
    FOREIGN KEY (tax_id) REFERENCES public.taxes(tax_id)
);

ALTER TABLE IF EXISTS public.product_taxes
    OWNER to postgres;

CREATE TABLE public.payment_status
(
    payment_status_id serial NOT NULL,
    payment_status_code character varying(25) NOT NULL,
    is_active boolean NOT NULL DEFAULT true,
    PRIMARY KEY (payment_status_id),
    CONSTRAINT uq_payment_status_code UNIQUE (payment_status_code)
);

ALTER TABLE IF EXISTS public.payment_status
    OWNER to postgres;

CREATE TABLE public.payment_method
(
    payment_method_id serial NOT NULL,
    payment_method_code character varying(50) NOT NULL,
    is_active boolean NOT NULL DEFAULT true,
    PRIMARY KEY (payment_method_id),
    CONSTRAINT uq_payment_method_code UNIQUE (payment_method_code)
);

ALTER TABLE IF EXISTS public.payment_method
    OWNER to postgres;


CREATE TABLE public.transactions
(
    transaction_id bigserial NOT NULL,
    customer_id integer NOT NULL,
    created_by integer NOT NULL,
    net_amount numeric(18, 2) NOT NULL CHECK (net_amount >=0),
    total_tax numeric(18, 2) NOT NULL CHECK (total_tax >=0) DEFAULT 0,
    total_amt numeric(18, 2) NOT NULL CHECK (total_amt = net_amount + total_tax AND total_amt >=0),
    payment_status_id integer NOT NULL,
    payment_method_id integer NOT NULL,
    transaction_time timestamp without time zone NOT NULL DEFAULT now(),
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (customer_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (created_by)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (payment_status_id)
        REFERENCES public.payment_status (payment_status_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (payment_method_id)
        REFERENCES public.payment_method (payment_method_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS public.transactions
    OWNER to postgres;

CREATE TABLE public.transaction_items
(
    transaction_item_id bigserial NOT NULL,
    transaction_id bigint NOT NULL,
    product_id integer NOT NULL,
    qty numeric(18, 2) NOT NULL CHECK (qty > 0),
    unit_price numeric(18, 2) NOT NULL CHECK (unit_price >=0),
    tax_amt numeric(18, 2) NOT NULL DEFAULT 0 CHECK (tax_amt >=0),
    PRIMARY KEY (transaction_item_id),
    FOREIGN KEY (transaction_id)
        REFERENCES public.transactions (transaction_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (product_id)
        REFERENCES public.products (product_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS public.transaction_items
    OWNER to postgres;

INSERT INTO payment_method(payment_method_code, is_active) VALUES('CASH', true);
INSERT INTO payment_method(payment_method_code, is_active) VALUES('QRIS', true);

INSERT INTO payment_status(payment_status_code, is_active) VALUES('PAID', true), ('UNPAID', true), ('CANCELED', true);

INSERT INTO products(product_name, price) VALUES
('Tumbler', 50000),
('Laptop', 10000000),
('Printer', 5300000);

INSERT INTO taxes(tax_name, rate) VALUES('PPN', 10), ('IMPORT', 5);

INSERT INTO product_taxes(product_id, tax_id) VALUES(1, 1), (3,2), (3,1);

INSERT INTO roles(role_name) VALUES('ADMIN'), ('NORMAL_USER');

INSERT INTO users(username, "password", email, is_active) VALUES('admin', '$2a$12$Znibc76sjAWmyrkZ7N.FM.rmyHLJehQwqGiQldrqEp3wmP2JXDxhy', 'dhyan@nova.co.id', true);
INSERT INTO users(username, "password", email, is_active) VALUES('user1', '$2a$12$Znibc76sjAWmyrkZ7N.FM.rmyHLJehQwqGiQldrqEp3wmP2JXDxhy', 'andreasdhyan@gmail.com', true);

INSERT INTO user_roles(user_id, role_id) VALUES (1,1), (1,2), (2,2);

