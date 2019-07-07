package de.trzpiot.example.api

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import de.trzpiot.example.core.port.driver.input.CreateUserInput
import de.trzpiot.example.core.port.driver.input.FollowUserInput
import de.trzpiot.example.core.port.driver.input.PostMessageInput
import de.trzpiot.example.core.port.driver.input.UnfollowUserInput
import de.trzpiot.example.core.port.driver.payload.CreateUserPayload
import de.trzpiot.example.core.port.driver.payload.FollowUserPayload
import de.trzpiot.example.core.port.driver.payload.PostMessagePayload
import de.trzpiot.example.core.port.driver.payload.UnfollowUserPayload
import de.trzpiot.example.core.port.driver.usecase.CreateUserUseCase
import de.trzpiot.example.core.port.driver.usecase.FollowUserUseCase
import de.trzpiot.example.core.port.driver.usecase.PostMessageUseCase
import de.trzpiot.example.core.port.driver.usecase.UnfollowUserUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
internal class Mutation
@Autowired
constructor(private val createUserUseCase: CreateUserUseCase,
            private val postMessageUseCase: PostMessageUseCase,
            private val followUserUseCase: FollowUserUseCase,
            private val unfollowUserUseCase: UnfollowUserUseCase)
    : GraphQLMutationResolver {

    fun createUser(input: CreateUserInput): CreateUserPayload {
        return createUserUseCase.createUser(input)
    }

    fun postMessage(input: PostMessageInput): PostMessagePayload {
        return postMessageUseCase.postMessage(input)
    }

    fun followUser(input: FollowUserInput): FollowUserPayload {
        return followUserUseCase.followUser(input)
    }

    fun unfollowUser(input: UnfollowUserInput): UnfollowUserPayload {
        return unfollowUserUseCase.unfollowUser(input)
    }
}