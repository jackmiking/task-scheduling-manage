package com.youfun.task.core.dto.request.app

import com.youfun.task.core.dto.AppInfo
import com.youfun.task.core.dto.TaskInfo

/**
 * @author jackmiking
 * @date 2022/11/4
 */
class AddTasksRequest(
    val appInfo: AppInfo,
    val taskInfoList: List<TaskInfo>
) {
    public constructor() : this(AppInfo(), listOf())
}