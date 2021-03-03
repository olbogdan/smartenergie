package net.grandcentrix.energy.sensor.repository.h2

import net.grandcentrix.energy.sensor.dto.Sensor
import net.grandcentrix.energy.sensor.repository.SensorDataSource
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class H2SensorDataSource(private val repository: SensorRepository) : SensorDataSource {

    override fun getSensors(): Collection<Sensor> = repository.findAll().map { it.toSensor() }

    @Transactional(readOnly = true)
    override fun getSensor(id: String): Sensor {
        val sensorEntity = repository.findByIdOrNull(id)
            ?: throw NoSuchElementException("Sensor with id $id does not exist")
        return sensorEntity.toSensor()
    }

    @Transactional
    override fun createSensor(sensor: Sensor): Sensor {
        if (repository.findById(sensor.id).isPresent) {
            throw IllegalArgumentException("Sensor with id ${sensor.id} already exist")
        }
        return repository.save(sensor.toSensorEntity()).toSensor()
    }

    @Transactional
    override fun updateSensor(sensor: Sensor): Sensor {
        val sensorEntity = repository.findByIdOrNull(sensor.id)
            ?: throw NoSuchElementException("Sensor with id ${sensor.id} does not exist")
        sensor.mac?.let { sensorEntity.mac = it }
        sensor.name?.let { sensorEntity.name = it }
        sensor.href?.let { sensorEntity.href = it }
        sensor.energyConsumption?.let { sensorEntity.energy = it }
        sensor.enabled?.let { sensorEntity.enabled = it }
        return repository.save(sensorEntity).toSensor()
    }

    override fun deleteSensor(id: String) {
        repository.deleteById(id)
    }
}

private fun SensorEntity.toSensor(): Sensor = Sensor(
    id = id,
    mac = mac,
    name = name,
    energyConsumption = energy,
    href = href,
    enabled = enabled
)

private fun Sensor.toSensorEntity(): SensorEntity = SensorEntity(
    id = id,
    mac = mac,
    name = name,
    energy = energyConsumption,
    href = href,
    enabled = enabled
)