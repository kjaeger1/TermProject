drop table photo;
drop table item;

create table item (
    id int not null auto_increment,
    category varchar(50) not null,
    name varchar(50) not null,
    description varchar(500),
    price decimal(5, 2) not null,
    active boolean not null,
    primary key (id),
    unique Category_Name (category, name)
);

insert into item (category, name, price, active) values (
    'I. Entrees', 'Entree1', 10, true);
insert into item (category, name, price, active) values (
    'I. Entrees', 'Entree2', 12, true);
insert into item (category, name, price, active) values (
    'II. Sides', 'Side1', 6, true);
insert into item (category, name, price, active) values (
    'II. Sides', 'Side2', 5, true);
insert into item (category, name, price, active) values (
    'III. Desserts', 'Dessert1', 2, true);
insert into item (category, name, price, active) values (
    'III. Desserts', 'Dessert2', 3, true);

create table photo (
    id int not null auto_increment,
    itemid int not null,
    name varchar(255) not null,
    type varchar(100) not null,
    size bigint,
    contents blob not null,
    primary key (id),
    foreign key (itemid) references item(id) on delete cascade
)
