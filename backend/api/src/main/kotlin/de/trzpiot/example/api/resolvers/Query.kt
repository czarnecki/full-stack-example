package de.trzpiot.example.api.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import de.trzpiot.example.core.api.Board
import de.trzpiot.example.core.api.Timeline
import de.trzpiot.example.core.api.User
import de.trzpiot.example.core.api.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Query
@Autowired
constructor(private val userService: UserService) : GraphQLQueryResolver {
    fun getTimeline(): List<Timeline> {
        return userService.getTimeline(userService.getCurrentUser())
    }

    fun getBoard(): List<Board> {
        return userService.getBoard(userService.getCurrentUser())
    }
}