package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.User

interface CreateUserPort {
    fun createUser(username: String, firstName: String, lastName: String): User
}