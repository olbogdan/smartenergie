package com.bogdanov.energy.sensor.client

import com.bogdanov.energy.sensor.dto.Sensor
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.patchForObject

@Service
class RestTemplateSensorClient(private val restTemplate: RestTemplate) : SensorClient {

    override fun updateSensor(url: String, sensor: Sensor): Sensor = restTemplate.patchForObject(url, sensor)

}