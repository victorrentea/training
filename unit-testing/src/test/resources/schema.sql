create table users (
	id int identity primary key,
	username varchar(20)
);

create table notifications (
	id int identity primary key,
	text varchar(255)
);
create table orders (
	id int identity primary key,
	reference varchar(20),
	created_by int foreign key references users(id)
);