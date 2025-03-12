
/*
Apagar depois visto que ja n vai ser necessario

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

 */


create table "user" (
    uid uuid unique primary key,
    name varchar(255) not null,
    email varchar(255) unique not null
);

create table club (
    cid uuid unique primary key,
    name varchar(255) unique not null,
    owner uuid references "user"(uid) /* necessario verificar se é assim */
);

create table court (
    crid uuid unique primary key,
    name varchar(255) not null,
    club uuid references club(cid) /* necessario verificar se é assim */
);

create table rental (
    rid uuid unique primary key,
    date date not null,
    duration int not null,
    "user" uuid references "user"(uid), /* necessario verificar se é assim */
    court uuid references court(crid) /* necessario verificar se é assim */
)
