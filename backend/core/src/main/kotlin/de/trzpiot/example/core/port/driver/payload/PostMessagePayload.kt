package de.trzpiot.example.core.port.driver.payload

import de.trzpiot.example.core.domain.User

data class PostMessagePayload(val postId: Long, val message: String, val author: User)