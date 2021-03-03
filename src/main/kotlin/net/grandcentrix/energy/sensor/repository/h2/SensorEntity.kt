package net.grandcentrix.energy.sensor.repository.h2

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "sensors")
data class SensorEntity(
    @Id
    val id: String,
    @Column(unique = true)
    var mac: String?,
    var name: String?,
    var href: String?,
    var energy: Int?,
    var enabled: Boolean?
)

