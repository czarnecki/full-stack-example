package de.trzpiot.example.core

class NotFollowingException(message: String) : RuntimeException(message)
class AlreadyFollowingException(message: String) : RuntimeException(message)
class UserNotFoundException(message: String) : RuntimeException(message)
class UsernameAlreadyUsed(message: String) : RuntimeException(message)