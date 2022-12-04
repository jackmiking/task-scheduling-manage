package com.youfun.task.starter.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "ufun.task")
class UfunTaskProperties() {
    var managerHost: String = ""
    var appName: String = ""
    var appProfile: String = "default"
    var version = 0
    var overwrite = false
    var callbackPath = "/ufun/cronTask/execute"
    var callbackHost: String? = null
}