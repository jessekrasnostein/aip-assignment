

/*

create view jdbcrealm_user (username, password) as
select username, password
from account;

create view jdbcrealm_group (username, groupname) as
select username, 'Users'
from account;


INSERT INTO ACCOUNT (ID, DATEOFBIRTH, EMAIL, FIRSTNAME, LASTNAME, PASSWORD, USERNAME, PLAN) 
	VALUES (1, '1989-11-03', 'jessekras@me.com', 'Jesse', 'Krasnostein', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'jessekras@me.com', 'FREE');

INSERT INTO ACCOUNT (ID, DATEOFBIRTH, EMAIL, FIRSTNAME, LASTNAME, PASSWORD, SUBSCRIPTIONPLAN, USERNAME, PLAN) 
	VALUES (1, '1989-11-03', 'jamesg@me.com', 'James', 'G', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'FREE', 'jamesg@me.com', 'FREE');


*/