package de.trzpiot.example.core.domain

import java.util.*

data class Post(
        var id: Long,
        var message: String,
        var creationDate: Date
)