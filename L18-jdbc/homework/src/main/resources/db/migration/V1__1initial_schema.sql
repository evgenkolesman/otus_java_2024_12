create schema if not exists jdbc_test;

create table jdbc_test.manager
(
    id bigint generated always as identity primary key,
    label text,
    param1 text
);
create table jdbc_test.client
(
    id   bigint primary key generated always as identity,
    name varchar(50)
);

