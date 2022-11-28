package com.youfun.task.app.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

/**
 * @author jackmiking
 * @date 2022/10/31
 */
@Entity
@Table(name = "ufun_task_scheduling")
class OneTimeTask(
    val app: String = "",
    val profile: String = "",
    val name: String = "",
    val subject: String = "",
    val associateId: String = "",
    val planTime: LocalDateTime = LocalDateTime.now(),
    var status: String = OneTimeTaskStatus.init.name,
    val execute: String = "",
    val updateTime:LocalDateTime= LocalDateTime.now(),
    @Id
    @GeneratedValue
    var id: Long? = null,
) {

}

enum class OneTimeTaskStatus {
    `init`, scheduling, running, error, cancel, finish
}