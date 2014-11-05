
create table user (
	id bigint not null auto_increment,
	login_name varchar(20) not null,
	full_name varchar(50) not null,
	email varchar(50) not null,
	password varchar(20) not null,
	created datetime not null,
	updated datetime,
	primary key (id)
);

create table message (
	id bigint not null auto_increment,
	user_id bigint not null,
	body varchar(65000),
	parent bigint,
	created datetime not null,
	primary key (id),
	foreign key (user_id) references user(id),
	foreign key (parent) references message(id)
);

create index author on message(user_id);


create table contact (
	source_id bigint not null,
	target_id bigint not null,
	last_modified datetime not null,
	status enum('Block', 'Follow', 'Inactive'),
	primary key (source_id, target_id),
	foreign key (source_id) references user(id),
	foreign key (target_id) references user(id)
)





