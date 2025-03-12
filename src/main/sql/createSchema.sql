drop table if exists students;
drop table if exists courses;

create table courses (
  cid serial primary key,
  name varchar(80)
);

create table students (
  number int primary key,
  name varchar(80),
  course int references courses(cid)
);




create table "user" (
    uid uuid primary key,
    name varchar(255) not null,
    email varchar(255) unique not null
);