package de.trzpiot.example.core.port.driver.usecase

import de.trzpiot.example.core.port.driver.input.UnfollowUserInput
import de.trzpiot.example.core.port.driver.payload.UnfollowUserPayload

interface UnfollowUserUseCase {
    fun unfollowUser(input: UnfollowUserInput): UnfollowUserPayload
}