package de.trzpiot.example.core

class SelfFollowException(message: String) : RuntimeException(message)
class NotFollowingException(message: String) : RuntimeException(message)
class AlreadyFollowingException(message: String) : RuntimeException(message)
class UserNotFoundException(message: String) : RuntimeException(message)