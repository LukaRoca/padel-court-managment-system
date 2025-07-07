begin;

create table if not exists users (
    uid serial primary key,
    token varchar(255) unique not null,
    name varchar(255) not null,
    email varchar(255) unique not null,
    password varchar(255) not null
);

create table if not exists club (
    cid serial primary key,
    name varchar(255) unique not null,
    owner int references users(uid)
);


create table if not exists court (
    crid serial unique primary key,
    name varchar(255) not null,
    club int references club(cid) on delete cascade
);

create table if not exists rental (
    rid serial primary key,
    date varchar(255) not null,
    initDuration int not null,
    endDuration int not null,
    usr int references users(uid),
    court int references court(crid) on delete cascade
)

commit;
