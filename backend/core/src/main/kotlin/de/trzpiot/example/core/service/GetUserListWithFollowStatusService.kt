package de.trzpiot.example.core.service

import de.trzpiot.example.core.port.driven.GetAuthenticatedUserByUsernamePort
import de.trzpiot.example.core.port.driven.GetCurrentlyLoggedInUserPort
import de.trzpiot.example.core.port.driven.GetUserListWithFollowStatusPort
import de.trzpiot.example.core.port.driver.payload.GetUserListWithFollowStatusPayload
import de.trzpiot.example.core.port.driver.usecase.GetUserListWithFollowStatusUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class GetUserListWithFollowStatusService
@Autowired
constructor(private val getCurrentlyLoggedInUserPort: GetCurrentlyLoggedInUserPort,
            private val getAuthenticatedUserByUsernamePort: GetAuthenticatedUserByUsernamePort,
            private val getUserListWithFollowStatusPort: GetUserListWithFollowStatusPort) : GetUserListWithFollowStatusUseCase {
    override fun getUserListWithFollowStatus(): GetUserListWithFollowStatusPayload {
        val currentlyLoggedInUserUsername = getCurrentlyLoggedInUserPort.getCurrentlyLoggedInUser()
        val authenticatedUser = getAuthenticatedUserByUsernamePort.getAuthenticatedUserByUsername(currentlyLoggedInUserUsername.username)
        return GetUserListWithFollowStatusPayload(currentlyLoggedInUserUsername, getUserListWithFollowStatusPort.getUserListWithFollowStatus(authenticatedUser))
    }
}