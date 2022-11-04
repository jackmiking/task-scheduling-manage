package com.youfun.task.app.entity

import javax.persistence.*


/**
 * @author jackmiking
 * @date 2022/10/28
 * maybe only need cron task.
 */
@Entity
@Table(name = "ufun_task")
class Task(
    val app: String = "", val profile: String = "",
    val name: String = "",  val cron: String = "",
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
) {
}