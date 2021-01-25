create database if not exists bookrental;
 
use bookrental;
 
drop table if exists books;

create table books (
   	id int not null auto_increment,
  	title varchar(50) not null,
   	author_id int,
  	year_of_public int,
  	rented BOOLEAN not null,
   	primary key (id)
);

insert into books values (NULL, 'Java for dummies', 1, 1999, false);
insert into books values (NULL, 'Romantycznosc', 2, 1830, false);

create table authors (
   	id int not null auto_increment,
	name varchar(30) not null,
	last_name varchar(30) not null,
   	primary key (id)
);

insert into authors values (NULL, 'Barry', 'Burd');
insert into authors values (NULL, 'Adam', 'Mickiewicz');

create table users (
   	id int not null auto_increment,
  	name varchar(50) not null,
  	lastName varchar(50) not null,
    dayOfBirth int,
  	monthOfBirth int,
  	yearOfBirth int,
  	email varchar(50) not null,
  	phoneNumber varchar(50),
   	primary key (id)
);

create table rents (
   	id int not null auto_increment,
   	user_id int not null,
   	book_id int not null,
  	date_of_rent DATE,
    date_of_return DATE,
   	primary key (id)
);

-- https://www3.ntu.edu.sg/home/ehchua/programming/java/JDBC_Basic.html#zz-2.3

