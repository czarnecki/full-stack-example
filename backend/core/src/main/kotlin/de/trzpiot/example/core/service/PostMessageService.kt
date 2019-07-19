package de.trzpiot.example.core.service

import de.trzpiot.example.core.domain.User
import de.trzpiot.example.core.port.driven.GetAuthenticatedUserByUsernamePort
import de.trzpiot.example.core.port.driven.GetCurrentlyLoggedInUserPort
import de.trzpiot.example.core.port.driven.PostMessagePort
import de.trzpiot.example.core.port.driver.input.PostMessageInput
import de.trzpiot.example.core.port.driver.payload.PostMessagePayload
import de.trzpiot.example.core.port.driver.usecase.PostMessageUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class PostMessageService
@Autowired
constructor(private val getCurrentlyLoggedInUserPort: GetCurrentlyLoggedInUserPort,
            private val getAuthenticatedUserByUsernamePort: GetAuthenticatedUserByUsernamePort,
            private val postMessagePort: PostMessagePort) : PostMessageUseCase {
    override fun postMessage(input: PostMessageInput): PostMessagePayload {
        val currentlyLoggedInUserUsername = getCurrentlyLoggedInUserPort.getCurrentlyLoggedInUser().username
        val authenticatedUser = getAuthenticatedUserByUsernamePort.getAuthenticatedUserByUsername(currentlyLoggedInUserUsername)
        return PostMessagePayload(postMessagePort.postMessage(authenticatedUser, input.message), User(authenticatedUser.username, authenticatedUser.givenName, authenticatedUser.familyName))
    }
}