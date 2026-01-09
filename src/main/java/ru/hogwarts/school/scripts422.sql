create table driver(
      id int primary key,
      name text not null,
      age int check (age > 0),
      documents boolean default false,
      id_cars int
);

create table cars(
      id int primary key,
      marka text not null,
      model text,
      price numeric(30, 2)
);

alter table driver add constraint constraint_driver_id_cars foreign key(id_cars) references cars (id);
