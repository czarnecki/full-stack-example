package de.trzpiot.example.core.port.driven

interface IsUserWithUsernamePresentPort {
    fun isUserWithUsernamePresent(username: String): Boolean
}