
delete from user_role_id;

delete from user;

delete from role;
insert into role(id,authority,description) value (1, "ROLE_ADMIN","Administration Account");
insert into role(id,authority,description) value (2, "ROLE_USER","Normal User Role Access");
update role set created_date=now();
commit;

insert into user(id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) value (1, "admin","admin", 1, 1 ,1, 1);
insert into user(id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) value (2, "mark","mark", 1, 1, 1, 1);
insert into user(id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) value (3, "jenny","jenny", 1, 1, 1, 1);
insert into user(id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) value (4, "nikki","nikki", 1, 1, 1, 1);
update user set created_date=now();
commit;

insert into user_role_id(user_id, role_id) value(1,1);
insert into user_role_id(user_id, role_id) value(1,2);
insert into user_role_id(user_id, role_id) value(2,2);
insert into user_role_id(user_id, role_id) value(3,2);
insert into user_role_id(user_id, role_id) value(4,2);
commit;