package de.trzpiot.example.core.port.driver.usecase

import de.trzpiot.example.core.port.driver.input.FollowUserInput
import de.trzpiot.example.core.port.driver.payload.FollowUserPayload

interface FollowUserUseCase {
    fun followUser(input: FollowUserInput): FollowUserPayload
}