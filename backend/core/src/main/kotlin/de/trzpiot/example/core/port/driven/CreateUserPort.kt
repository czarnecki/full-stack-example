package de.trzpiot.example.core.port.driven

interface CreateUserPort {
    fun createUser(username: String): Long
}