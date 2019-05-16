package de.trzpiot.example.core.api

interface UserService {
    fun findUser(id: Long): User
    fun getCurrentUser(): User
    fun getTimeline(user: User): List<Timeline>
    fun getBoard(user: User): List<Board>
    fun followUser(follower: User, follows: User)
    fun post(author: User, post: Post)
}