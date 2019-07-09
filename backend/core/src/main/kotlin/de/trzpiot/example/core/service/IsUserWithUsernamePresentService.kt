package de.trzpiot.example.core.service

import de.trzpiot.example.core.port.driven.IsUserWithUsernamePresentPort
import de.trzpiot.example.core.port.driver.input.IsUserWithUsernamePresentInput
import de.trzpiot.example.core.port.driver.payload.IsUserWithUsernamePresentPayload
import de.trzpiot.example.core.port.driver.usecase.IsUserWithUsernamePresentUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class IsUserWithUsernamePresentService
@Autowired
constructor(private val isUserWithUsernamePresentPort: IsUserWithUsernamePresentPort) : IsUserWithUsernamePresentUseCase {
    override fun isUserWithUsernamePresent(isUserWithUsernamePresentInput: IsUserWithUsernamePresentInput): IsUserWithUsernamePresentPayload {
        return IsUserWithUsernamePresentPayload(isUserWithUsernamePresentInput.username, isUserWithUsernamePresentPort.isUserWithUsernamePresent(isUserWithUsernamePresentInput.username))
    }
}