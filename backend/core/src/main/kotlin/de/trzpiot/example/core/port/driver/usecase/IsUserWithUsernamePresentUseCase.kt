package de.trzpiot.example.core.port.driver.usecase

import de.trzpiot.example.core.port.driver.input.IsUserWithUsernamePresentInput
import de.trzpiot.example.core.port.driver.payload.IsUserWithUsernamePresentPayload

interface IsUserWithUsernamePresentUseCase {
    fun isUserWithUsernamePresent(isUserWithUsernamePresentInput: IsUserWithUsernamePresentInput): IsUserWithUsernamePresentPayload
}