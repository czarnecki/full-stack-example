package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.AuthenticatedUser

interface GetAuthenticatedUserByUsernamePort {
    fun getAuthenticatedUserByUsername(username: String): AuthenticatedUser
}