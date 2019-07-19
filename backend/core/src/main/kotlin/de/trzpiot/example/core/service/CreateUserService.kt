package de.trzpiot.example.core.service

import de.trzpiot.example.core.UsernameAlreadyUsed
import de.trzpiot.example.core.domain.User
import de.trzpiot.example.core.port.driven.CreateUserPort
import de.trzpiot.example.core.port.driven.IsUserWithUsernamePresentPort
import de.trzpiot.example.core.port.driver.input.CreateUserInput
import de.trzpiot.example.core.port.driver.payload.CreateUserPayload
import de.trzpiot.example.core.port.driver.usecase.CreateUserUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class CreateUserService
@Autowired
constructor(private val createUserPort: CreateUserPort,
            private val isUserWithUsernamePresentPort: IsUserWithUsernamePresentPort) : CreateUserUseCase {
    override fun createUser(createUserInput: CreateUserInput): CreateUserPayload {
        if (isUserWithUsernamePresentPort.isUserWithUsernamePresent(createUserInput.username))
            throw UsernameAlreadyUsed("The username \"${createUserInput.username}\" is already used.")

        val user = createUserPort.createUser(createUserInput.username, createUserInput.givenName, createUserInput.familyName)
        return CreateUserPayload(User(user.username, user.givenName, user.familyName))
    }
}