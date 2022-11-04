package com.youfun.task.core.api

/**
 * @author jackmiking
 * @date 2022/10/31
 */
data class ApiResponse<T>(
    val code: Int,
    val message: String? = null,
    val data: T? = null,
) {
}
