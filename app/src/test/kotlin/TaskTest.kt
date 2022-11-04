import com.youfun.task.app.TaskApplication
import com.youfun.task.app.repository.TaskRepository
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource

/**
 * @author jackmiking
 * @date 2022/11/1
 */
@SpringBootTest(classes = [TaskApplication::class])
class TaskTest {
    @Resource
    lateinit var taskRepository: TaskRepository;

    @Test
    fun assertHaveRecord() {
        val count = taskRepository.findAll().count();
        assert(count > 0)
    }
}