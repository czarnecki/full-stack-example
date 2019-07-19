package de.trzpiot.example.core.service

import de.trzpiot.example.core.port.driven.GetAuthenticatedUserByUsernamePort
import de.trzpiot.example.core.port.driven.GetCurrentlyLoggedInUserPort
import de.trzpiot.example.core.port.driven.GetTimelineFromUserPort
import de.trzpiot.example.core.port.driver.payload.GetTimelineFromUserPayload
import de.trzpiot.example.core.port.driver.usecase.GetTimelineFromUserUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class GetTimelineFromUserService
@Autowired
constructor(private val getCurrentlyLoggedInUserPort: GetCurrentlyLoggedInUserPort,
            private val getAuthenticatedUserByUsernamePort: GetAuthenticatedUserByUsernamePort,
            private val getTimelineFromUserPort: GetTimelineFromUserPort) : GetTimelineFromUserUseCase {
    override fun getTimelineFromUser(): GetTimelineFromUserPayload {
        val currentlyLoggedInUserUsername = getCurrentlyLoggedInUserPort.getCurrentlyLoggedInUser()
        val authenticatedUser = getAuthenticatedUserByUsernamePort.getAuthenticatedUserByUsername(currentlyLoggedInUserUsername.username)
        return GetTimelineFromUserPayload(currentlyLoggedInUserUsername, getTimelineFromUserPort.getTimelineFromUser(authenticatedUser))
    }
}