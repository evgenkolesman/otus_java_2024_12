
create table client
(
    id   bigint not null primary key generated always as identity,
    name varchar(50),
    address_id bigint,
    phones_id bigint
);

