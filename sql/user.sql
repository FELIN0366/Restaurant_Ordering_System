create table if not exists user
(
    User_id    varchar(10) not null comment '客人名称'
        primary key,
    User_pwd   int(8)      null comment '客人密码',
    User_mail  varchar(20) not null comment '电子邮箱',
    User_phone varchar(20) not null comment '电话号码'
);

