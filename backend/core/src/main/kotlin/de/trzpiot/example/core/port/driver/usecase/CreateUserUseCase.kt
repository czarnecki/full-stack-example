package de.trzpiot.example.core.port.driver.usecase

import de.trzpiot.example.core.port.driver.input.CreateUserInput
import de.trzpiot.example.core.port.driver.payload.CreateUserPayload

interface CreateUserUseCase {
    fun createUser(createUserInput: CreateUserInput): CreateUserPayload
}