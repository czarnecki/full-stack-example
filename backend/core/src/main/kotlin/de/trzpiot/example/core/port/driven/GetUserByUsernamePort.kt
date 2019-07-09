package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.User

interface GetUserByUsernamePort {
    fun getUserByUsername(username: String): User
}