package com.youfun.task.app.entity

import java.time.LocalDateTime
import javax.persistence.*


/**
 * @author jackmiking
 * @date 2022/10/28
 * maybe only need cron task.
 */
@Entity
@Table(name = "ufun_task")
class CronTask(

    val app: String = "", val profile: String = "",
    val name: String = "", val cron: String = "",
    var execute: String = "",
    var updateTime: LocalDateTime = LocalDateTime.now(),
    var version: Int = 0,
    var status: String = CronTaskStatus.init.name,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
) {
}

enum class CronTaskStatus {
    `init`,scheduling, cancel
}