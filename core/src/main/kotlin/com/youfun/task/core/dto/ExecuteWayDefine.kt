package com.youfun.task.core.dto

import com.youfun.task.core.client.OkClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @author jackmiking
 * @date 2022/11/2
 */
sealed class ExecuteWayDefine(val type: ExecuteType = ExecuteType.url) {

}

enum class ExecuteType {
    ufun, url;
}

data class UrlExecuteWayDefine(

    val url: String,
    val jsonBody: String,
) : ExecuteWayDefine() {

    fun execute(): Pair<Int, String?> {
        val request = Request.Builder().url(url)
            .post(jsonBody.toRequestBody("application/json".toMediaType())).build()
        return OkClient.client.newCall(request)
            .execute().use { response ->
                return response.code to response.body.toString()
            }
    }
}

data class MethodExecuteWayDefine(
    val name: String,
    val jsonBody: String,
) : ExecuteWayDefine(ExecuteType.ufun) {

}