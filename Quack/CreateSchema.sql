drop schema MC851-Quack;
create schema MC851-Quack;

create table `user` (
	id bigint unsigned not null auto_increment primary key,
	created timestamp not null,
	updated timestamp not null,
	username varchar(32) not null,
	email varchar(128) not null,
	password varchar(32) not null,
	role tinyint unsigned not null,
	
	constraint username_id unique (username),
	constraint email_id unique (mail)
);

create table message (
	id bigint unsigned not null auto_increment primary key,
	created timestamp not null,
	body longtext not null,
  user_id bigint unsigned not null,
  parent bigint unsigned not null
);

create table contact (
	id bigint unsigned not null auto_increment primary key,
  source bigint unsigned not null,
  target bigint unsigned not null,
	created timestamp not null,
  blocked tinyint(1) unsigned not null
);
