create sequence department_seq;

create table department
(
    id   int not null primary key,

    name varchar(255) not null
);

create sequence employee_seq;

create table employee
(
    id            int not null primary key,

    last_name     varchar(255) not null,
    first_name    varchar(255) not null,

    department_id int          not null,

    foreign key (department_id) references department (id)
);

create view v_employee as
select e.id         as employee_id,
       e.first_name as employee_first_name,
       e.last_name  as employee_last_name,
       d.name       as department_name
from employee e
         join department d on e.department_id = d.id;
