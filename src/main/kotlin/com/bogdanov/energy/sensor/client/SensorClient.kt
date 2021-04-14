package com.bogdanov.energy.sensor.client

import com.bogdanov.energy.sensor.dto.Sensor

interface SensorClient {

    fun updateSensor(url: String, sensor: Sensor): Sensor
}