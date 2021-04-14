package com.bogdanov.energy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EnergyApplication

fun main(args: Array<String>) {
    runApplication<EnergyApplication>(*args)
}
