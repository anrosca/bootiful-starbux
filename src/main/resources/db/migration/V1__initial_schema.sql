create sequence drink_sequence start with 1000;

create table drink(
    id bigint primary key default nextval('drink_sequence'),
    name varchar(100) not null,
    drink_type varchar(40) not null
);
