
create table users (
    uid serial primary key,
    token varchar(255) unique not null,
    name varchar(255) not null,
    email varchar(255) unique not null
);

create table club (
    cid serial primary key,
    name varchar(255) unique not null,
    owner int references users(uid)
);


create table court (
    crid serial unique primary key,
    name varchar(255) not null,
    club int references club(cid)
);

create table rental (
    rid serial primary key,
    date varchar(255) not null,
    initDuration int not null,
    endDuration int not null,
    usr int references users(uid),
    court int references court(crid)
)
