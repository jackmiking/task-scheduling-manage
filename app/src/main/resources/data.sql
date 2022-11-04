delete from ufun_task;
insert  into ufun_task
values (1, 'hello', 'helloapp', 'test', 'cron', '0/1 * * * *'),
       (2, 'hello2', 'helloapp', 'test', 'cron', '0/1 * * * *'),
       (3,'hello3','helloapp','test','cron','0/1 * * * *')
       ;
delete from ufun_task_scheduling;
insert into ufun_task_scheduling
values (1, 'init', current_date(), 1, null),
       (2, 'init', current_date(), 2, null),
       (3, 'init', current_date(), null, null)
;