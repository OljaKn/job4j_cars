create table history_owner(
    id serial primary key,
    owner_id int not null references owners(id),
    car_id int not null references car(id),
    startAt timestamp,
    endAt timestamp,
    unique (owner_id, car_id)
);