package com.testassignment.core.utils

import java.util.concurrent.TimeUnit

object DateUtility {

    fun getTimeDifference(previousTimeStamp: Long = 0L, currentTimeStamp: Long = System.currentTimeMillis()): Long {
        val previousTimeInMinutes = TimeUnit.MILLISECONDS.toMinutes(previousTimeStamp)
        val currentTimeInMinutes = TimeUnit.MILLISECONDS.toMinutes(currentTimeStamp)
        if (previousTimeInMinutes < currentTimeInMinutes) {
            val timeDifference = currentTimeInMinutes - previousTimeInMinutes
            return timeDifference
        }
        return 0L
    }

}