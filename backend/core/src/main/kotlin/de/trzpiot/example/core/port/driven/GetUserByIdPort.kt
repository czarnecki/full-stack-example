package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.User

interface GetUserByIdPort {
    fun getUserById(id: Long): User
}