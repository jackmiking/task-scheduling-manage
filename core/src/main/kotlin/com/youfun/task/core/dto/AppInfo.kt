package com.youfun.task.core.dto

import org.hibernate.validator.constraints.Length

/**
 * @author jackmiking
 * @date 2022/11/4
 */
data class AppInfo(
    @Length(max = 30)
    val app: String, @Length(max=15) val profile: String, val version: Int,
    val overWrite: Boolean = true
) {
}