package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.User

interface FollowUserPort {
    fun followUser(following: User, followed: User)
}