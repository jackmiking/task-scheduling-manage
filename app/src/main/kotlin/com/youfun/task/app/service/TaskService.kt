package com.youfun.task.app.service

import com.youfun.task.app.entity.Task
import com.youfun.task.app.entity.TaskScheduling
import com.youfun.task.app.repository.TaskRepository
import com.youfun.task.core.dto.CronTaskType
import com.youfun.task.core.dto.OneTimeTaskType
import com.youfun.task.core.dto.request.app.AddTasksRequest
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.annotation.Resource

/**
 * @author jackmiking
 * @date 2022/11/4
 */
@Service
class TaskService {

    @Resource
    lateinit var taskRepository: TaskRepository;

    fun addTasks(tasksRequest: AddTasksRequest): Void {

        tasksRequest.apply {
            taskInfoList.map {
                when (val plan = it.plan) {
                    is CronTaskType -> {
                        Task(appInfo.app, appInfo.profile, it.name, plan.cron)
                    }

                    is OneTimeTaskType -> {
                        TaskScheduling(
                            LocalDateTime.ofInstant(Instant.ofEpochMilli(plan.planTime), ZoneId.of("+8")), "init"
                        )
                    }
                }
            }.partition { it is Task }

        }

        TODO()
    }


}

