create table if not exists ufun_task
(
    id      bigint      not null primary key auto_increment,
    name    varchar(30) not null,
    app     varchar(30) not null,
    profile varchar(10) not null,
    type    varchar(20) not null,
    cron    varchar(30) not null
);
create table if not exists ufun_task_scheduling
(
    id            bigint      not null primary key auto_increment,
    status        varchar(20) not null,
    plan_time     timestamp   not null,
    task_id       bigint,
    status_detail text
)