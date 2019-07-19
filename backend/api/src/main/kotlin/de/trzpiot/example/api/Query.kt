package de.trzpiot.example.api

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import de.trzpiot.example.core.port.driver.payload.GetCurrentlyLoggedInUserPayload
import de.trzpiot.example.core.port.driver.payload.GetTimelineFromUserPayload
import de.trzpiot.example.core.port.driver.payload.GetUserListWithFollowStatusPayload
import de.trzpiot.example.core.port.driver.usecase.GetCurrentlyLoggedInUserUseCase
import de.trzpiot.example.core.port.driver.usecase.GetTimelineFromUserUseCase
import de.trzpiot.example.core.port.driver.usecase.GetUserListWithFollowStatusUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
internal class Query
@Autowired
constructor(private val getTimelineFromUserUseCase: GetTimelineFromUserUseCase,
            private val getUserListWithFollowStatusUseCase: GetUserListWithFollowStatusUseCase,
            private val getCurrentlyLoggedInUserUseCase: GetCurrentlyLoggedInUserUseCase)
    : GraphQLQueryResolver {

    fun getTimelineFromUser(): GetTimelineFromUserPayload {
        return getTimelineFromUserUseCase.getTimelineFromUser()
    }

    fun getUserListWithFollowStatus(): GetUserListWithFollowStatusPayload {
        return getUserListWithFollowStatusUseCase.getUserListWithFollowStatus()
    }

    fun getCurrentlyLoggedInUser(): GetCurrentlyLoggedInUserPayload {
        return getCurrentlyLoggedInUserUseCase.getCurrentlyLoggedInUser()
    }
}