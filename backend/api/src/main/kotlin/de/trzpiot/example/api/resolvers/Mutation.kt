package de.trzpiot.example.api.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import de.trzpiot.example.core.api.Post
import de.trzpiot.example.core.api.UserService
import graphql.PublicApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Mutation
@Autowired
constructor(private val userService: UserService) : GraphQLMutationResolver {
    fun post(message: String): Boolean {
        val post = Post()
        post.message = message
        userService.post(userService.getCurrentUser(), post)
        return true
    }

    fun follow(id: Long): Boolean {
        val follows = userService.findUser(id)
        userService.followUser(userService.getCurrentUser(), follows)
        return true
    }
}