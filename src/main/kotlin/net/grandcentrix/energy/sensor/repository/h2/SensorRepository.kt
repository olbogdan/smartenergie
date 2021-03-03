package net.grandcentrix.energy.sensor.repository.h2

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SensorRepository : CrudRepository<SensorEntity, String>