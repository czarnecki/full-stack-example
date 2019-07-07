package de.trzpiot.example.core.port.driver.payload

import de.trzpiot.example.core.domain.User

data class FollowUserPayload(val following: User, val followed: User, val followRelationshipId: Long)