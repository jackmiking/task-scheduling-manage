package com.youfun.task.app.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.youfun.task.app.entity.CronTask
import com.youfun.task.app.entity.OneTimeTask
import com.youfun.task.app.entity.OneTimeTaskStatus
import com.youfun.task.app.repository.CronTaskRepository
import com.youfun.task.core.dto.AppInfo
import com.youfun.task.core.dto.CronTaskType
import com.youfun.task.core.dto.OneTimeTaskType
import com.youfun.task.core.dto.request.app.AddTasksRequest
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.annotation.Resource
import javax.transaction.Transactional

/**
 * @author jackmiking
 * @date 2022/11/4
 */
@Service
open class TaskService {

    @Resource
    lateinit var cronTaskRepository: CronTaskRepository;

    @Resource
    lateinit var oneTimeTaskService: OneTimeTaskService

    @Resource
    lateinit var objectMapper: ObjectMapper
    fun toString(obj: Any): String {
        return objectMapper.writeValueAsString(obj)
    }

    fun addTasks(tasksRequest: AddTasksRequest) {
        tasksRequest.run {
            var (cronList, oneTimeList) = taskInfoList.map {
                when (val plan = it.plan) {
                    is CronTaskType -> {
                        CronTask(
                            appInfo.app, appInfo.profile, it.name, plan.cron, toString(it.execute), LocalDateTime.now(),
                            appInfo.version
                        )
                    }

                    is OneTimeTaskType -> {
                        OneTimeTask(
                            appInfo.app, appInfo.profile, it.name, plan.subject, plan.associativeId,
                            LocalDateTime.ofInstant(Instant.ofEpochMilli(plan.planTime), ZoneId.of("+8")),
                            OneTimeTaskStatus.`init`.name, it.execute.toString()
                        )
                    }
                }
            }.partition { it is CronTask }
            if (cronList.isNotEmpty()) {
                addCronTask(cronList as List<CronTask>, appInfo)
            }
            if (oneTimeList.isNotEmpty()) {
                oneTimeTaskService.addOneTimeTask(oneTimeList as List<OneTimeTask>)
            }
        }

    }

    @Transactional
    open fun addCronTask(cronList: List<CronTask>, appInfo: AppInfo) {
        val locked = lock(appInfo.app, appInfo.profile, appInfo.version)
        assert(locked)
        try {
            val exist: Boolean =
                cronTaskRepository.existsByAppAndProfileAndVersion(appInfo.app, appInfo.profile, appInfo.version)
            if (exist) {
                if (appInfo.overWrite) {
                    cronTaskRepository.deleteAllByAppAndProfileAndVersion(appInfo.app, appInfo.profile, appInfo.version)
                } else {
                    return
                }
            }
            cronTaskRepository.saveAll(cronList)
        } catch (e: Exception) {
        } finally {
            unlock(appInfo.app, appInfo.profile, appInfo.version)
        }
    }

    fun lock(app: String, profile: String, version: Int): Boolean {
        return true;
    }

    fun unlock(app: String, profile: String, version: Int): Boolean {
        return true
    }


}

