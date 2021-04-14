package com.bogdanov.energy.sensor.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalArgumentException

@ControllerAdvice(annotations = [RestController::class])
class RestControllerExceptionHandler {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpHeaders().apply { contentType = MediaType.TEXT_PLAIN }, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBedRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpHeaders().apply { contentType = MediaType.TEXT_PLAIN }, HttpStatus.BAD_REQUEST)
}