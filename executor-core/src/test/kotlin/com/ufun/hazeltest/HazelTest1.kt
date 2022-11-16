package com.ufun.hazeltest

import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance
import org.junit.jupiter.api.Test

class HazelTest1 {
    @Test
    fun  test(){
        Hazelcast.newHazelcastInstance()
    }
}