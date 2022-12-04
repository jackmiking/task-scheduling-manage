package com.youfun.task.starter.annotations

annotation class CronScheduled(val name:String, val cron:String,val profiles:Array<String>, val callbackBody:String="")
