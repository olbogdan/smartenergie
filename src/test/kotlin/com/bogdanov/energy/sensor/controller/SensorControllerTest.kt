package com.bogdanov.energy.sensor.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.bogdanov.energy.sensor.dto.Sensor
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional //to get the default Repository rollback behaviour for each test
internal class SensorControllerTest @Autowired constructor(val mockMvc: MockMvc, val objectMapper: ObjectMapper) {

    private val baseUrl = "/api/v1/sensors"

    private val sensors = listOf(
        Sensor("42", "s1:ff:ff:ff:ff:ff", "sensor 1", "https://sensoradress/s1", 100, false)
    )

    @Nested
    @DisplayName("GET /api/v1/sensors")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetSensors {

        @Test
        fun `should return all sensors`() {
            // when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status {
                        isOk()
                        content {
                            contentType(MediaType.APPLICATION_JSON)
                            json(objectMapper.writeValueAsString(sensors))
                        }
                    }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/v1/sensors/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetSensor {

        @Test
        fun `should return a sensor with the given id`() {
            // given
            val expectedSensor = sensors[0]

            // when/then
            mockMvc.get("$baseUrl/${expectedSensor.id}")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(expectedSensor))
                    }
                }
        }

        @Test
        fun `should return Not Found if the id does not exist`() {
            // given
            val id = "not_existing_id"

            // when/then
            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("POST /api/v1/sensors")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostSensor {

        @Test
        fun `should add a new sensor`() {
            // given
            val newSensor = Sensor("4", "x3:xx:xx:xx:xx", "living room", "https://192.168.1.44", 100, true)

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newSensor)
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newSensor))
                    }
                }

            mockMvc.get("$baseUrl/${newSensor.id}")
                .andExpect {
                    content {
                        json(objectMapper.writeValueAsString(newSensor))
                    }
                }
        }

        @Test
        fun `should return BAD REQUEST if sensor with given id already exist`() {
            // given
            val invalidSensor = Sensor(id = "42", mac = "xx:xx")

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidSensor)
            }

            // then
            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }
    }

    @Nested
    @DisplayName("PATCH /api/v1/sensors")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchSensor {

        @Test
        fun `should update a name of existing sensor from iPhone`() {
            // given
            val updatedSensor = Sensor(id = "42", name = "updated name")

            // when
            val performPatchRequest = mockMvc.patch(baseUrl) {
                header("user-agent", "iPhone")
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedSensor)
            }

            // then
            performPatchRequest
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                    }
                    jsonPath("$.id") { value("42") }
                    jsonPath("$.name") { value("updated name") }
                }

            mockMvc.get("$baseUrl/42")
                .andExpect {
                    jsonPath("$.id") { value("42") }
                    jsonPath("$.name") { value("updated name") }
                }
        }

        @Test
        fun `should return BAD REQUEST if no sensor to update with given id exist`() {
            // given
            val invalidSensor = Sensor(id = "does_not_exist", enabled = true)

            // when
            val performPatchRequest = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidSensor)
            }

            // then
            performPatchRequest
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("DELETE /api/v1/sensors/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteSensor {

        @Test
        fun `should delete the sensor with the given id`() {
            // given
            val id = 42

            // when/then
            mockMvc.delete("$baseUrl/$id")
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }

            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }
}