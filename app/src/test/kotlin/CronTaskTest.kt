import com.youfun.task.app.TaskApplication
import com.youfun.task.app.entity.CronTask
import com.youfun.task.app.job.JobSchedule
import com.youfun.task.app.repository.CronTaskRepository
import com.youfun.task.core.dto.CronTaskType
import com.youfun.task.core.dto.TaskInfo
import com.youfun.task.core.dto.UrlTaskExecutor
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource

/**
 * @author jackmiking
 * @date 2022/11/1
 */
@SpringBootTest(classes = [TaskApplication::class])
class CronTaskTest {
    @Resource
    lateinit var cronTaskRepository: CronTaskRepository;

    @Test
    fun assertHaveRecord() {
        val count = cronTaskRepository.findAll().count();
        assert(count > 0)
    }

    @Resource
    lateinit var jobSchedule: JobSchedule;


    @Test
    fun runTask() {
        var taskInfo = TaskInfo(
            "test", "hello", CronTaskType("0/10 * * * * *"),
            UrlTaskExecutor(
                "http://localhost:8080/api/task/callbackTest",
                """{"name":"bbq"}"""
            )
        )
        println(taskInfo.toString());

        println("go to sleep--------------")
        Thread.sleep(10000000)
        println("finish--------------")

    }

    @Test
    fun addTaskTest() {
        val cron=CronTask("hello","test","abc","0/4 * * * * *","""{"url":"http://localhost:8080/api/task/callbackTest","jsonBody":"{\"name\":\"cron1\"}"}"""
        ,version = 221112,id=5)
        cronTaskRepository.save(cron)
        println(cronTaskRepository.findAll())
    }
}