package com.youfun.task.app.api

import com.youfun.task.app.service.TaskService
import com.youfun.task.core.api.ApiResponse
import com.youfun.task.core.dto.request.app.AddTasksRequest
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author jackmiking
 * @date 2022/10/31
 */
@RestController
@RequestMapping("/api/task")
class TaskApi {
    @Resource
    lateinit var taskService: TaskService;

    @PostMapping("/add")
    fun addTask(@RequestBody tasks: AddTasksRequest): ApiResponse<Void> {
        taskService.addTasks(tasks)
        return ApiResponse(200);
    }


    @PostMapping("/callbackTest")
    fun testCall(): ApiResponse<String> = ApiResponse(200, "good", """{"aa":"bb"}""")
}