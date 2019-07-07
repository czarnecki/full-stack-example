package de.trzpiot.example.core.service

import de.trzpiot.example.core.port.driven.GetUserByIdPort
import de.trzpiot.example.core.port.driver.input.GetUserByIdInput
import de.trzpiot.example.core.port.driver.payload.GetUserByIdPayload
import de.trzpiot.example.core.port.driver.usecase.GetUserByIdUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class GetUserByIdService
@Autowired
constructor(private val getUserByIdPort: GetUserByIdPort) : GetUserByIdUseCase {
    override fun getUserById(getUserByIdInput: GetUserByIdInput): GetUserByIdPayload {
        return GetUserByIdPayload(getUserByIdPort.getUserById(getUserByIdInput.userId))
    }
}