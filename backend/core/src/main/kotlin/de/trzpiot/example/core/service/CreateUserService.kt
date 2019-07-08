package de.trzpiot.example.core.service

import de.trzpiot.example.core.port.driven.CreateUserPort
import de.trzpiot.example.core.port.driver.input.CreateUserInput
import de.trzpiot.example.core.port.driver.payload.CreateUserPayload
import de.trzpiot.example.core.port.driver.usecase.CreateUserUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class CreateUserService
@Autowired
constructor(private val createUserPort: CreateUserPort) : CreateUserUseCase {
    override fun createUser(createUserInput: CreateUserInput): CreateUserPayload {
        return CreateUserPayload(createUserPort.createUser(createUserInput.username))
    }
}