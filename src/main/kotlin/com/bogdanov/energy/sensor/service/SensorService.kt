package com.bogdanov.energy.sensor.service

import com.bogdanov.energy.sensor.client.SensorClient
import com.bogdanov.energy.sensor.repository.SensorDataSource
import com.bogdanov.energy.sensor.dto.Sensor
import org.springframework.mobile.device.Device
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SensorService(private val dataSource: SensorDataSource,
                    private val sensorClient: SensorClient,
                    private val pushNotification: PushNotification
) {

    fun getSensors(): Collection<Sensor> = dataSource.getSensors()

    fun getSensor(id: String): Sensor = dataSource.getSensor(id)

    fun addSensor(sensor: Sensor): Sensor = dataSource.createSensor(sensor)

    fun deleteSensor(id: String) {
        dataSource.deleteSensor(id)
    }

    @Transactional
    fun updateSensor(sensor: Sensor, device: Device): Sensor =
        // isNormal device is not a mobile or tablet device.
        if (device.isNormal) {
            handleUpdateInitiatedBySensorItself(sensor)
        } else {
            handleUpdateInitiatedFromMobileClient(sensor)
        }


    private fun handleUpdateInitiatedBySensorItself(sensor: Sensor): Sensor {
        val updatedSensor = dataSource.updateSensor(sensor)
        pushNotification.notifyOnSensorStateChanged(updatedSensor)
        return updatedSensor
    }

    private fun handleUpdateInitiatedFromMobileClient(sensor: Sensor): Sensor {
        val updatedSensor = sensorClient.updateSensor(getSensorHref(sensor), sensor)
        return dataSource.updateSensor(updatedSensor)
    }

    private fun getSensorHref(sensor: Sensor): String =
        sensor.href
            ?: dataSource.getSensor(sensor.id).href
            ?: throw IllegalArgumentException("Sensor with id ${sensor.id} does not have an associated href")
}