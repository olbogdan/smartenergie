package net.grandcentrix.energy.sensor.service

import net.grandcentrix.energy.sensor.dto.Sensor
import org.springframework.stereotype.Service

interface PushNotificationService {
    fun notifyOnSensorStateChanged(sensor: Sensor)
}

/**
 * todo: implement push notifications
 */
@Service
class MockPushNotificationService : PushNotificationService {

    override fun notifyOnSensorStateChanged(sensor: Sensor) {
        //stub
    }
}