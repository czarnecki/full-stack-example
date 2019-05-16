package de.trzpiot.example.core.api

data class User(
        var id: Long? = null,
        var subject: String? = null,
        var username: String? = null,
        var follows: MutableList<Follow>? = mutableListOf(),
        var posts: MutableList<Post>? = mutableListOf()
)