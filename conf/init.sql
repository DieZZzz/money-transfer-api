drop table if exists Account;
create table Account(id int primary key, amount decimal(18, 4) not null, currency varchar(3));
insert into Account values (1, 100, 'USD');
insert into Account values (2, 100, 'EUR');