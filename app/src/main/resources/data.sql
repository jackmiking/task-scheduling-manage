delete from ufun_cron_task;
insert  into ufun_task
values (1,  'helloapp', 'test', 'hello',221111,current_timestamp, '0/1 * * * *','init','{"url":"http://localhost:8080/api/task/callbackTest","jsonBody":{"name":"cron1"}}'),
       (2,  'helloapp', 'test', 'hello2',221111,current_timestamp, '0/1 * * * *','init','{"url":"http://localhost:8080/api/task/callbackTest","jsonBody":{"name":"cron2"}}'),
       (3,'helloapp','test','hello3',221111,current_timestamp, '0/1 * * * *','init','{"url":"http://localhost:8080/api/task/callbackTest","jsonBody":{"name":"cron3"}}')
       ;
delete from ufun_one_time_task;
insert into ufun_one_time_task
values (1, 'helloapp','test','one1','','','init',current_date,'{"url":"http://localhost:8080/api/task/callbackTest","jsonBody":{"name":"bbq"}}'),
       (1, 'helloapp','test','one2','','','init',current_date,'{"url":"http://localhost:8080/api/task/callbackTest","jsonBody":{"name":"bbq2"}}'),
       (1, 'helloapp','test','one3','','','init',current_date,'{"url":"http://localhost:8080/api/task/callbackTest","jsonBody":{"name":"bbq3"}}'),
;