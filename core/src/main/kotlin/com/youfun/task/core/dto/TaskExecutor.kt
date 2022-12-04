package com.youfun.task.core.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.youfun.task.core.client.OkClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @author jackmiking
 * @date 2022/11/2
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type",defaultImpl = UrlTaskExecutor::class)
sealed class TaskExecutor(val type: ExecuteType = ExecuteType.URL) {
    abstract fun execute(): Pair<Int, String?>
}

enum class ExecuteType {
    URL;
}
@JsonTypeName("URL")
data class UrlTaskExecutor(

    val url: String="",
    val jsonBody: String="",
) : TaskExecutor() {
    override fun execute(): Pair<Int, String?> {
        val request = Request.Builder().url(url)
            .post(jsonBody.toRequestBody("application/json".toMediaType())).build()
        return OkClient.client.newCall(request)
            .execute().use { response ->
                return response.code to response.body?.string()
            }
    }
}
