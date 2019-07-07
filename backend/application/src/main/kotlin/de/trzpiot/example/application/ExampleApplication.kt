package de.trzpiot.example.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["de.trzpiot.example"])

class ExampleApplication

fun main(args: Array<String>) {
    runApplication<ExampleApplication>(*args)
}