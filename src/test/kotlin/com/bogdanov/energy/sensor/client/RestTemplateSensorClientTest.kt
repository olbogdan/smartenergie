package com.bogdanov.energy.sensor.client

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import com.bogdanov.energy.sensor.dto.Sensor
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate

internal class RestTemplateSensorClientTest {

    private val restTemplate: RestTemplate = mockk()

    private val sensorService = RestTemplateSensorClient(restTemplate)

    @Test
    fun `should call its RestTemplate to update sensor`() {
        // given
        val sensor = Sensor(id = "1", enabled = true)
        every {
            restTemplate.patchForObject("https://192", sensor, Sensor::class.java)
        } returns sensor

        // when
        sensorService.updateSensor("https://192", sensor)

        // then
        verify(exactly = 1) { restTemplate.patchForObject("https://192", sensor, Sensor::class.java) }
    }
}