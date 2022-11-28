package com.youfun.task.app.repository

import com.youfun.task.app.entity.CronTask
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

/**
 * @author jackmiking
 * @date 2022/11/1
 */
@Repository
interface CronTaskRepository : CrudRepository<CronTask, Long>, JpaSpecificationExecutor<CronTask> {
    fun findByStatusAndUpdateTimeAfter(status: String, date: LocalDateTime): List<CronTask>
    fun existsByAppAndProfileAndVersion(app: String, profile: String, version: Int): Boolean
    fun deleteAllByAppAndProfileAndVersion(app: String, profile: String, version: Int): Int

    @Modifying
    @Query("update CronTask c set c.status=?1 where c.app=?2 and c.profile=?3 and c.version<?4")
    fun updateStatusByAppAndProfileAndVersionLt(status: String, app: String, profile: String, version: Int): Int
}