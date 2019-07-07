package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.User

interface IsFollowingPort {
    fun isFollowing(following: User, followed: User): Boolean
}