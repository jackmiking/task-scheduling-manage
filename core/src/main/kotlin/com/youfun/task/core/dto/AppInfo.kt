package com.youfun.task.core.dto

/**
 * @author jackmiking
 * @date 2022/11/4
 */
data class AppInfo(
    val app: String, val profile: String, val version: Int,
    val overWrite: Boolean = true
) {
}