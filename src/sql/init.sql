create database if not exists statistis_num;

create table if not exists statistic_num (
    `id` int auto_increment,
    `num_arr_str` varchar(100) not null,
    primary key(id)
)engine=innodb default charset=UTF8MB4;