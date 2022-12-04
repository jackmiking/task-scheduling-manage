package com.youfun.task.core.dto

/**
 * @author jackmiking
 * @date 2022/11/2
 */
data class TaskInfo(
    val profiles: String,
    val name: String,
    val plan: TaskType,
    val execute: TaskExecutor
){
    constructor():this("","",CronTaskType(),UrlTaskExecutor())
}
