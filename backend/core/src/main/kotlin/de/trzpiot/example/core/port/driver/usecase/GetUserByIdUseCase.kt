package de.trzpiot.example.core.port.driver.usecase

import de.trzpiot.example.core.port.driver.input.GetUserByIdInput
import de.trzpiot.example.core.port.driver.payload.GetUserByIdPayload

interface GetUserByIdUseCase {
    fun getUserById(getUserByIdInput: GetUserByIdInput): GetUserByIdPayload
}