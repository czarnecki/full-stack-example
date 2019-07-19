package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.AuthenticatedUser

interface FollowUserPort {
    fun followUser(following: AuthenticatedUser, followed: AuthenticatedUser)
}