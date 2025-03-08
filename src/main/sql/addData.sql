insert into courses(name) values ('LEIC');
insert into courses(name) values ('LEIM');
insert into courses(name) values ('LEETC');
insert into courses(name) values ('LEIRT');


insert into students(course, number, name) values (1, 12345, 'Alice');
insert into students(course, number, name) select cid as course, 12346 as number, 'Bob' as name from courses where name = 'LEIC';
insert into students(course, number, name) select cid as course, 50484 as number, 'Afonso' as name from courses where name = 'LEIC';
insert into students(course, number, name) select cid as course, 51690 as number, 'Bernardo' as name from courses where name = 'LEIC';
