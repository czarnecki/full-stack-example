package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.Post
import de.trzpiot.example.core.domain.User

interface PostMessagePort {
    fun postMessage(user: User, message: String): Post
}