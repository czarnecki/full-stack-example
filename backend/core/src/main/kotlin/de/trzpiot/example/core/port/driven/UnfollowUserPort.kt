package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.User

interface UnfollowUserPort {
    fun unfollowUser(following: User, followed: User): Long
}