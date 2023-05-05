package com.youfun.task.app.entity

import jakarta.persistence.*
import java.util.*


/**
 * @author jackmiking
 * @date 2022/10/28
 * maybe only need cron task.
 */
@Entity
@Table(name = "ufun_cron_task")
class CronTask(

    val app: String = "", val profile: String = "",
    val name: String = "", val cron: String = "",
    var execute: String = "",
    var updateTime: Date = Date(),
    var version: Int = 0,
    var status: String = CronTaskStatus.SCHEDULING.name,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
}

enum class CronTaskStatus {
    SCHEDULING, CANCEL, DEPRECATED
}