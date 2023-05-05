package com.youfun.task.app.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

/**
 * @author jackmiking
 * @date 2022/10/31
 */
@Entity
@Table(name = "ufun_one_time_task")
class OneTimeTask(
    val app: String = "",
    val profile: String = "",
    val name: String = "",
    val subject: String = "",
    val subjectId: String = "",
    val planTime: Date = Date(),
    var status: String = OneTimeTaskStatus.INIT.name,
    val execute: String = "",
    val updateTime: Date = Date(),
    @Id
    @GeneratedValue
    var id: Long? = null,
) {

}

enum class OneTimeTaskStatus {
    INIT, SCHEDULING, RUNNING, ERROR, CANCEL, FINISH
}