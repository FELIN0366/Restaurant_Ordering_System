create table if not exists order_info
(
    Order_id     int(4) auto_increment comment '订单号'
        primary key,
    Order_table  int(3) unsigned zerofill null comment '桌号',
    Order_status int(1) default 0         null comment '结账情况',
    Order_Amount int                      null comment '食物总数',
    Order_debt   float                    null comment '账单价格',
    User_id      varchar(10)              null comment '客人名称',
    Order_date   varchar(50)              null comment '下单日期',
    Order_time   varchar(50)              null comment '下单时间'
);

