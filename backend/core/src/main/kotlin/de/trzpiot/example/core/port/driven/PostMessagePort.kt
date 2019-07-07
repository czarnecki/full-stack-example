package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.User

interface PostMessagePort {
    fun postMessage(author: User, message: String): Long
}