package com.youfun.task.app.service

import com.youfun.task.app.entity.OneTimeTask
import com.youfun.task.app.repository.OneTimeTaskRepository
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service
class OneTimeTaskService {
    @Resource
    lateinit var oneTimeTaskRepository: OneTimeTaskRepository
    fun addOneTimeTask(taskList: List<OneTimeTask>): MutableIterable<OneTimeTask>? {
        return oneTimeTaskRepository.saveAll(taskList)
    }
}