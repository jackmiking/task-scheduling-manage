package com.youfun.task.app.service

import com.youfun.task.app.entity.OneTimeTask
import com.youfun.task.app.repository.OneTimeTaskRepository
import jakarta.annotation.Resource
import org.springframework.stereotype.Service

@Service
class OneTimeTaskService {
    @Resource
    lateinit var oneTimeTaskRepository: OneTimeTaskRepository
    fun addOneTimeTask(taskList: List<OneTimeTask>): MutableIterable<OneTimeTask>? {
        return oneTimeTaskRepository.saveAll(taskList)
    }
}