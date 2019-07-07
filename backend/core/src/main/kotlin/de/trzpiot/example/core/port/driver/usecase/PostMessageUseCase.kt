package de.trzpiot.example.core.port.driver.usecase

import de.trzpiot.example.core.port.driver.input.PostMessageInput
import de.trzpiot.example.core.port.driver.payload.PostMessagePayload

interface PostMessageUseCase {
    fun postMessage(input: PostMessageInput): PostMessagePayload
}