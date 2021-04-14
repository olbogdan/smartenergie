package com.bogdanov.energy.sensor.client

import com.bogdanov.energy.sensor.dto.Sensor
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

/**
 * TODO: Use [RestTemplateSensorClient] when hardware is ready
 */
@Service
@Primary
class MockRestTemplateSensorClient : SensorClient {

    override fun updateSensor(url: String, sensor: Sensor): Sensor = sensor
}