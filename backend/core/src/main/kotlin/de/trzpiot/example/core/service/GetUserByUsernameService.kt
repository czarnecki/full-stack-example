package de.trzpiot.example.core.service

import de.trzpiot.example.core.port.driven.GetUserByUsernamePort
import de.trzpiot.example.core.port.driver.input.GetUserByUsernameInput
import de.trzpiot.example.core.port.driver.payload.GetUserByUsernamePayload
import de.trzpiot.example.core.port.driver.usecase.GetUserByUsernameUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class GetUserByUsernameService
@Autowired
constructor(private val getUserByUsernamePort: GetUserByUsernamePort) : GetUserByUsernameUseCase {
    override fun getUserByUsername(getUserByUsernameInput: GetUserByUsernameInput): GetUserByUsernamePayload {
        return GetUserByUsernamePayload(getUserByUsernamePort.getUserByUsername(getUserByUsernameInput.username))
    }
}