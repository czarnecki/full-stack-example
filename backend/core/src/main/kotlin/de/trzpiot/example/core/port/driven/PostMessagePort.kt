package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.AuthenticatedUser
import de.trzpiot.example.core.domain.Post

interface PostMessagePort {
    fun postMessage(user: AuthenticatedUser, message: String): Post
}