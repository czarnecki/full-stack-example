package de.trzpiot.example.core.api

import java.util.*

data class Post(
        var id: Long? = null,
        var message: String? = null,
        var creationDate: Date = Date()
)