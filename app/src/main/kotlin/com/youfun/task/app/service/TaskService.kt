package com.youfun.task.app.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.youfun.task.app.entity.CronTask
import com.youfun.task.app.entity.CronTaskStatus
import com.youfun.task.app.entity.OneTimeTask
import com.youfun.task.app.entity.OneTimeTaskStatus
import com.youfun.task.app.repository.CronTaskRepository
import com.youfun.task.core.dto.AppInfo
import com.youfun.task.core.dto.CronTaskType
import com.youfun.task.core.dto.OneTimeTaskType
import com.youfun.task.core.dto.request.app.AddTasksRequest
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.Resource
import javax.transaction.Transactional

/**
 * @author jackmiking
 * @date 2022/11/4
 */

@Service
open class TaskService(@Autowired open  var cronTaskRepository: CronTaskRepository
                       , @Resource open var oneTimeTaskService: OneTimeTaskService,
                       @Autowired open var objectMapper: ObjectMapper) {


    fun toString(obj: Any): String {
        return objectMapper.writeValueAsString(obj)
    }

    fun addTasks(tasksRequest: AddTasksRequest) {
        val updateTime=Date()
        tasksRequest.run {
            var (cronList, oneTimeList) = taskInfoList.map {
                when (val plan = it.plan) {
                    is CronTaskType -> {
                        CronTask(
                            appInfo.app, appInfo.profile, it.name, plan.cron, toString(it.execute),updateTime,
                            appInfo.version
                        )
                    }

                    is OneTimeTaskType -> {
                        OneTimeTask(
                            appInfo.app, appInfo.profile, it.name, plan.subject, plan.subjectId,
                            Date(plan.planTime),
                            OneTimeTaskStatus.INIT.name, it.execute.toString()
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
            cronTaskRepository.updateStatusByAppAndProfileAndVersionLt(
                CronTaskStatus.DEPRECATED.name,
                appInfo.app,
                appInfo.profile,
                appInfo.version
            )
        } catch (e: Exception) {
            val log=LoggerFactory.getLogger(TaskService::class.java)
            log.error(e.message,e)
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

