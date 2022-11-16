import com.youfun.task.app.TaskApplication
import com.youfun.task.app.job.JobExecutor
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
    lateinit var jobExecutor: JobExecutor;


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
        jobExecutor.executeTask(taskInfo)
        println("go to sleep--------------")
        Thread.sleep(10000000)
        println("finish--------------")

    }
    @Test
    fun addTaskTest(){

    }
}