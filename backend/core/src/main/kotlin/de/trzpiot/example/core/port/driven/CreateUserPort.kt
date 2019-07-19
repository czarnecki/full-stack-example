package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.AuthenticatedUser

interface CreateUserPort {
    fun createUser(username: String, givenName: String, familyName: String): AuthenticatedUser
}