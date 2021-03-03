package net.grandcentrix.energy.sensor.controller

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import net.grandcentrix.energy.sensor.dto.Sensor
import net.grandcentrix.energy.sensor.service.SensorService
import org.springframework.http.HttpStatus
import org.springframework.mobile.device.Device
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

private const val SENSOR_EXAMPLE = """
                            {
                            "id": "42",
                            "mac": "xx:xx:xx:xx",
                            "name": "my sensor",
                            "href": "https://192.168.1.1",
                            "enabled": true,
                            "energy_consumption": 123
                            }
                        """

@RestController
@RequestMapping("api/v1/sensors")
class SensorController(private val service: SensorService) {

    @GetMapping(produces = ["application/json"])
    fun getSensors(): Collection<Sensor> = service.getSensors()

    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    fun getSensor(@PathVariable id: String): Sensor = service.getSensor(id)

    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun addSensor(
        @SwaggerRequestBody(content = [Content(examples = [ExampleObject(value = SENSOR_EXAMPLE)])])
        @RequestBody
        sensor: Sensor
    ): Sensor =
        service.addSensor(sensor)

    @PatchMapping(produces = ["application/json"])
    fun updateSensor(
        @RequestBody sensor: Sensor,
        @Parameter(hidden = true)
        device: Device
    ): Sensor =
        service.updateSensor(sensor, device)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSensor(@PathVariable id: String) {
        service.deleteSensor(id)
    }
}