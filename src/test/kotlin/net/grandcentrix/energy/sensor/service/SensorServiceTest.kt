package net.grandcentrix.energy.sensor.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.grandcentrix.energy.sensor.client.SensorClient
import net.grandcentrix.energy.sensor.repository.SensorDataSource
import net.grandcentrix.energy.sensor.dto.Sensor
import org.junit.jupiter.api.Test
import org.springframework.mobile.device.Device

internal class SensorServiceTest {

    private val dataSource: SensorDataSource = mockk(relaxed = true)

    private val sensorClient: SensorClient = mockk(relaxed = true)

    private val pushNotificationService: PushNotificationService = mockk(relaxed = true)

    private val sensorService = SensorService(dataSource, sensorClient, pushNotificationService)

    @Test
    fun `should call its data source to retrieve sensors`() {
        // when
        sensorService.getSensors()

        // then
        verify(exactly = 1) { dataSource.getSensors() }
    }

    @Test
    fun `should call its data source to retrieve sensor by id`() {
        // when
        sensorService.getSensor("sensor_id")

        // then
        verify(exactly = 1) { dataSource.getSensor("sensor_id") }
    }

    @Test
    fun `should call its data source to add a sensor`() {
        // given
        val sensor = Sensor("id")

        // when
        sensorService.addSensor(sensor)

        // then
        verify(exactly = 1) { dataSource.createSensor(sensor) }
    }

    @Test
    fun `should call its data source to delete a sensor`() {
        // when
        sensorService.deleteSensor("sensor_id")

        // then
        verify(exactly = 1) { dataSource.deleteSensor("sensor_id") }
    }

    @Test
    fun `should call its data source to update a sensor from mobile`() {
        // given
        val sensor = Sensor(id = "id", href = "https://192")
        val device = object : Device {
            override fun isNormal(): Boolean = false
        }
        every { sensorClient.updateSensor(sensor.href!!, sensor) } returns sensor

        // when
        sensorService.updateSensor(sensor, device)

        // then
        verify(exactly = 1) { sensorClient.updateSensor("https://192", sensor) }
        verify(exactly = 1) { dataSource.updateSensor(sensor) }
    }

    @Test
    fun `should call its data source to update a sensor from sensor`() {
        // given
        val sensor = Sensor("id")
        val device = object : Device {
            override fun isNormal(): Boolean = true
        }
        every { dataSource.updateSensor(sensor) } returns sensor

        // when
        sensorService.updateSensor(sensor, device)

        // then
        verify(exactly = 1) { dataSource.updateSensor(sensor) }
        verify(exactly = 1) { pushNotificationService.notifyOnSensorStateChanged(sensor) }
    }
}