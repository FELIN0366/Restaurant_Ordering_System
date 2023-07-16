create table if not exists food
(
    Food_id     int(2) unsigned zerofill auto_increment comment '食物序号'
        primary key,
    Food_name   varchar(30)  not null comment '食物名称',
    Food_type   varchar(10)  not null comment '食物种类',
    Food_price  float        not null comment '食物价格',
    Food_image  varchar(100) not null comment '食物照片',
    Food_detail varchar(100) null comment '食物介绍'
);

