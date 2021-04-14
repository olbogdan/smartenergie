package com.bogdanov.energy.sensor.service

import com.bogdanov.energy.sensor.dto.Sensor
import org.springframework.stereotype.Service

interface PushNotification {
    fun notifyOnSensorStateChanged(sensor: Sensor)
}

/**
 * todo: implement push notifications
 */
@Service
class MockPushNotificationService : PushNotification {

    override fun notifyOnSensorStateChanged(sensor: Sensor) {
        //stub
    }
}