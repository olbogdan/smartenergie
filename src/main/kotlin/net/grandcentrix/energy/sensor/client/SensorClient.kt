package net.grandcentrix.energy.sensor.client

import net.grandcentrix.energy.sensor.dto.Sensor

interface SensorClient {

    fun updateSensor(url: String, sensor: Sensor): Sensor
}