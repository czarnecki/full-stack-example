package de.trzpiot.example.core.port.driver.usecase

import de.trzpiot.example.core.port.driver.payload.GetCurrentlyLoggedInUserPayload

interface GetCurrentlyLoggedInUserUseCase {
    fun getCurrentlyLoggedInUser(): GetCurrentlyLoggedInUserPayload
}