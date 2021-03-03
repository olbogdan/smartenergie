package net.grandcentrix.energy.sensor.service

import net.grandcentrix.energy.sensor.client.SensorClient
import net.grandcentrix.energy.sensor.repository.SensorDataSource
import net.grandcentrix.energy.sensor.dto.Sensor
import org.springframework.mobile.device.Device
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SensorService(private val dataSource: SensorDataSource,
                    private val sensorClient: SensorClient,
                    private val pushNotificationService: PushNotificationService) {

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


    private fun handleUpdateInitiatedFromMobileClient(sensor: Sensor): Sensor {
        val updatedSensor = sensorClient.updateSensor(getSensorHref(sensor), sensor)
        return dataSource.updateSensor(updatedSensor)
    }

    private fun handleUpdateInitiatedBySensorItself(sensor: Sensor): Sensor {
        val updatedSensor = dataSource.updateSensor(sensor)
        pushNotificationService.notifyOnSensorStateChanged(updatedSensor)
        return updatedSensor
    }

    private fun getSensorHref(sensor: Sensor): String =
        sensor.href
            ?: dataSource.getSensor(sensor.id).href
            ?: throw IllegalArgumentException("Sensor with id ${sensor.id} does not have an associated href")
}