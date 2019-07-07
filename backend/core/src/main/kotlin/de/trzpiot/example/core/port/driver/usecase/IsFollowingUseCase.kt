package de.trzpiot.example.core.port.driver.usecase

import de.trzpiot.example.core.port.driver.input.IsFollowingInput
import de.trzpiot.example.core.port.driver.payload.IsFollowingPayload

interface IsFollowingUseCase {
    fun isFollowing(input: IsFollowingInput): IsFollowingPayload
}