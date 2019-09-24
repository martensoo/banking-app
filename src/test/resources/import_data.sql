delete from transaction;
delete from account;
delete from user;

insert into user(id, first_name, last_name, birth_date) values (1001, 'Mari', 'Maasikas', to_date('01.02.2002','dd.mm.yyyy'));
insert into user(id, first_name, last_name, birth_date) values (1002, 'Virve', 'Vaarikas', to_date('01.01.2001','dd.mm.yyyy'));

insert into account(id, owner_id, iban, balance_in_cents, currency) values (10001, 1001, 'IT60 X054 2811 1010 0000 0123 456', 10000, 'EUR');
insert into account(id, owner_id, iban, balance_in_cents, currency) values (10002, 1002, 'IT60 X054 2811 1010 0000 0123 457', 10000, 'EUR');