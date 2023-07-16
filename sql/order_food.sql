create table if not exists order_food
(
    Order_id       int(4)      null,
    Food_name      varchar(20) null,
    Food_price     float       null,
    Food_amount    int(2)      null,
    Food_sum_price float       null,
    `index`        int auto_increment
        primary key
);

