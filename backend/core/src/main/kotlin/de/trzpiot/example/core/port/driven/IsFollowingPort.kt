package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.AuthenticatedUser

interface IsFollowingPort {
    fun isFollowing(following: AuthenticatedUser, followed: AuthenticatedUser): Boolean
}