package com.bogdanov.energy.sensor.repository

import com.bogdanov.energy.sensor.dto.Sensor

interface SensorDataSource {

    fun getSensors(): Collection<Sensor>

    fun getSensor(id: String): Sensor

    fun createSensor(sensor: Sensor): Sensor

    fun updateSensor(sensor: Sensor): Sensor

    fun deleteSensor(id: String)
}