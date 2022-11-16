package com.youfun.task.app.repository

import com.youfun.task.app.entity.OneTimeTask
import org.springframework.data.repository.CrudRepository

interface OneTimeTaskRepository : CrudRepository<OneTimeTask, Long> {
    fun findByPlanTimeBetweenAndStatus(min:Long,max:Long,status:String):List<OneTimeTask>

}