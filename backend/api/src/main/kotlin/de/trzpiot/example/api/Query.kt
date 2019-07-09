package de.trzpiot.example.api

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import de.trzpiot.example.core.port.driver.input.GetTimelineFromUserInput
import de.trzpiot.example.core.port.driver.input.GetUserByUsernameInput
import de.trzpiot.example.core.port.driver.input.GetUserListWithFollowStatusInput
import de.trzpiot.example.core.port.driver.payload.GetTimelineFromUserPayload
import de.trzpiot.example.core.port.driver.payload.GetUserByUsernamePayload
import de.trzpiot.example.core.port.driver.payload.GetUserListWithFollowStatusPayload
import de.trzpiot.example.core.port.driver.usecase.GetTimelineFromUserUseCase
import de.trzpiot.example.core.port.driver.usecase.GetUserByUsernameUseCase
import de.trzpiot.example.core.port.driver.usecase.GetUserListWithFollowStatusUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
internal class Query
@Autowired
constructor(private val getTimelineFromUserUseCase: GetTimelineFromUserUseCase,
            private val getUserListWithFollowStatusUseCase: GetUserListWithFollowStatusUseCase,
            private val getUserByUsernameUseCase: GetUserByUsernameUseCase)
    : GraphQLQueryResolver {

    fun getTimelineFromUser(input: GetTimelineFromUserInput): GetTimelineFromUserPayload {
        return getTimelineFromUserUseCase.getTimelineFromUser(input)
    }

    fun getUserListWithFollowStatus(input: GetUserListWithFollowStatusInput): GetUserListWithFollowStatusPayload {
        return getUserListWithFollowStatusUseCase.getUserListWithFollowStatus(input)
    }

    fun getUserByUsername(input: GetUserByUsernameInput): GetUserByUsernamePayload {
        return getUserByUsernameUseCase.getUserByUsername(input)
    }
}