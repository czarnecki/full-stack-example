package de.trzpiot.example.core.port.driver.usecase

import de.trzpiot.example.core.port.driver.input.GetUserByUsernameInput
import de.trzpiot.example.core.port.driver.payload.GetUserByUsernamePayload

interface GetUserByUsernameUseCase {
    fun getUserByUsername(getUserByUsernameInput: GetUserByUsernameInput): GetUserByUsernamePayload
}