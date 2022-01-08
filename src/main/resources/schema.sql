-- database schema for testing purposes.
drop table account_holders;
drop table transactions;
drop type transaction_type;
drop database tornado;

create database tornado;
\c tornado;

-- note: GENERATED ALWAYS AS IDENTITY
CREATE TABLE account_holders (
    name           VARCHAR(100),
    address        VARCHAR(100),
    city           VARCHAR(100),
    state          VARCHAR(20),
    catname        VARCHAR(10),
    ssn            vARCHAR(20),
    cellphone      VARCHAR(20),
    email          VARCHAR(100),
    account_number BIGINT  PRIMARY KEY);

-- create the transaction types enum
create type transaction_type as enum ('BUY', 'SELL', 'DEPOSIT', 'WITHDRAW');

-- create transactions table
create table transactions
(
    account_number   BIGINT,
    constraint transactiond_fk

        foreign key (account_number) REFERENCES account_holders (account_number),

    symbol           VARCHAR(20),
    price            double precision,
    timestamp        timestamp(6) with time zone,
    transaction_type transaction_type,
    shares           double precision
);

create unique index transactions_ix1 on transactions(account_number, timestamp);
