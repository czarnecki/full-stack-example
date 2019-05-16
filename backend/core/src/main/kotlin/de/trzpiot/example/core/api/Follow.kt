package de.trzpiot.example.core.api

import java.util.*

data class Follow(
        var id: Long? = null,
        var dateOfFollowing: Date = Date(),
        var follower: User? = null,
        var follows: User? = null
)