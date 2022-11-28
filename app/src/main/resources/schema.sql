
drop table ufun_cron_task;
drop table ufun_one_time_task;
create table if not exists ufun_cron_task
(
    id          bigint      not null primary key auto_increment,
    app         varchar(30) not null,
    profile     varchar(15) not null,
    name        varchar(64) not null,
    version     int         not null,
    update_time datetime    not null,
    cron        varchar(30) not null,
    status      varchar(10) not null,
    execute     tinytext    not null

);
create table if not exists ufun_one_time_task
(
    id             bigint      not null primary key auto_increment,
    app            varchar(30) not null,
    profile        varchar(15) not null,
    name           varchar(64) not null,
    subject        varchar(30) not null default '',
    subject_id varchar(64) not null default '',
    status         varchar(10) not null,
    plan_time      timestamp   not null,
    execute        tinytext    not null,
    update_time    timestamp   not null default current_timestamp()

)