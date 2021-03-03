package net.grandcentrix.energy.sensor.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "sensor")
data class Sensor(
    @Schema(required=true)
    @JsonProperty("id")
    val id: String,
    @JsonProperty("mac")
    var mac: String? = null,
    @JsonProperty("name")
    var name: String? = null,
    @JsonProperty("href")
    var href: String? = null,
    @field:Schema(name = "energy_consumption")
    @field:JsonProperty("energy_consumption")
    var energyConsumption: Int? = null,
    @JsonProperty("enabled")
    var enabled: Boolean? = null
)