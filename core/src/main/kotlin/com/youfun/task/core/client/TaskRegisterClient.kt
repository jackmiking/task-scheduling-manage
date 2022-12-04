package com.youfun.task.core.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.youfun.task.core.dto.request.app.AddTasksRequest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Component
import javax.annotation.Resource

@Component
class TaskRegisterClient {
    @Resource
    lateinit var objectMapper: ObjectMapper
    fun register(host: String, addTasksRequest: AddTasksRequest):Pair<Int,String?> {
        val request = Request.Builder().url("$host/api/task/add")
            .post(objectMapper.writeValueAsString(addTasksRequest).toRequestBody("application/json".toMediaType()))
            .build();
        OkClient.client.newCall(request).execute().use { response ->
            return response.code to response.body?.string()
        }
    }
}