package de.trzpiot.example.core.service

import de.trzpiot.example.core.port.driven.GetUserByIdPort
import de.trzpiot.example.core.port.driven.PostMessagePort
import de.trzpiot.example.core.port.driver.input.PostMessageInput
import de.trzpiot.example.core.port.driver.payload.PostMessagePayload
import de.trzpiot.example.core.port.driver.usecase.PostMessageUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class PostMessageService
@Autowired
constructor(private val postMessagePort: PostMessagePort, private val getUserByIdPort: GetUserByIdPort) : PostMessageUseCase {
    override fun postMessage(input: PostMessageInput): PostMessagePayload {
        val user = getUserByIdPort.getUserById(input.userId)
        return PostMessagePayload(postMessagePort.postMessage(user, input.message), user)
    }
}