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
class TaskScheduling(
    val planTime: LocalDateTime,
    var status: String = "",
    val taskId: Long? = null,
    var statusDetail: String? = null,
    @Id
    @GeneratedValue
    var id: Long? = null,
) {

}