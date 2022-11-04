package com.youfun.task.app.repository

import com.youfun.task.app.entity.Task
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * @author jackmiking
 * @date 2022/11/1
 */
@Repository
interface TaskRepository : CrudRepository<Task, Long> {
}