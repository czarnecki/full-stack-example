package de.trzpiot.example.core.port.driver.payload

import de.trzpiot.example.core.domain.User

data class IsFollowingPayload(val following: User, val followed: User, val isFollowing: Boolean)