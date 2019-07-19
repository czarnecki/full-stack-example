package de.trzpiot.example.core.domain

data class AuthenticatedUser(
        var id: Long,
        var username: String,
        var givenName: String,
        var familyName: String
)