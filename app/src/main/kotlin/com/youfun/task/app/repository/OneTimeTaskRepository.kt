package com.youfun.task.app.repository

import com.youfun.task.app.entity.OneTimeTask
import org.springframework.data.repository.CrudRepository
import java.util.*

interface OneTimeTaskRepository : CrudRepository<OneTimeTask, Long> {
    fun findByPlanTimeBetweenAndStatus(min: Date, max:Date, status:String):List<OneTimeTask>

}