package de.trzpiot.example.core.port.driver.payload

import de.trzpiot.example.core.domain.Post
import de.trzpiot.example.core.domain.User

data class PostMessagePayload(val post: Post, val user: User)