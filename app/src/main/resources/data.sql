delete from ufun_cron_task;
insert  into ufun_cron_task
values (21,  'helloapp', 'test', 'hello',221111,current_timestamp, '0/10 * * * * *','SCHEDULING','{"url":"http://localhost:8080/api/task/callbackTest","jsonBody":"{\"name\":\"cron1\"}"}'),
       (22,  'helloapp', 'test', 'hello2',221111,current_timestamp, '0/5 * * * * *','SCHEDULING','{"url":"http://localhost:8080/api/task/callbackTest","jsonBody":"{\"name\":\"cron1\"}"}'),
       (23,'helloapp','test','hello3',221111,current_timestamp, '0/3 * * * * *','SCHEDULING','{"url":"http://localhost:8080/api/task/callbackTest","jsonBody":"{\"name\":\"cron1\"}"}')
       ;
delete from ufun_one_time_task;
insert into ufun_one_time_task
values (21, 'helloapp','test','one1','','','INIT',current_timestamp,'{"url":"http://localhost:8080/api/task/callbackTest","jsonBody":"{\"name\":\"cron1\"}"}',current_timestamp ),
       (22, 'helloapp','test','one2','','','INIT',current_timestamp,'{"url":"http://localhost:8080/api/task/callbackTest","jsonBody":"{\"name\":\"cron1\"}"}',current_timestamp),
       (23, 'helloapp','test','one3','','','INIT',current_timestamp,'{"url":"http://localhost:8080/api/task/callbackTest","jsonBody":"{\"name\":\"cron1\"}"}',current_timestamp)
;