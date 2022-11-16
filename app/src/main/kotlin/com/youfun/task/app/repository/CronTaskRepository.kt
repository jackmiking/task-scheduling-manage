package com.youfun.task.app.repository

import com.youfun.task.app.entity.CronTask
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

/**
 * @author jackmiking
 * @date 2022/11/1
 */
@Repository
interface CronTaskRepository : CrudRepository<CronTask, Long> {
    fun findByUpdateTimeAfter(date: LocalDateTime): List<CronTask>
    fun existsByAppAndProfileAndVersion(app: String, profile: String, version: Int): Boolean
    fun deleteAllByAppAndProfileAndVersion(app: String, profile: String, version: Int): Int
}