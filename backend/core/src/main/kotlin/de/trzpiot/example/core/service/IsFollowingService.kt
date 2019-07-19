package de.trzpiot.example.core.service

import de.trzpiot.example.core.domain.User
import de.trzpiot.example.core.port.driven.GetAuthenticatedUserByUsernamePort
import de.trzpiot.example.core.port.driven.GetCurrentlyLoggedInUserPort
import de.trzpiot.example.core.port.driven.IsFollowingPort
import de.trzpiot.example.core.port.driver.input.IsFollowingInput
import de.trzpiot.example.core.port.driver.payload.IsFollowingPayload
import de.trzpiot.example.core.port.driver.usecase.IsFollowingUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class IsFollowingService
@Autowired
constructor(private val getCurrentlyLoggedInUserPort: GetCurrentlyLoggedInUserPort,
            private val getAuthenticatedUserByUsernamePort: GetAuthenticatedUserByUsernamePort,
            private val isFollowingPort: IsFollowingPort) : IsFollowingUseCase {
    override fun isFollowing(input: IsFollowingInput): IsFollowingPayload {
        val currentlyLoggedInUserUsername = getCurrentlyLoggedInUserPort.getCurrentlyLoggedInUser().username
        val following = getAuthenticatedUserByUsernamePort.getAuthenticatedUserByUsername(currentlyLoggedInUserUsername)
        val followed = getAuthenticatedUserByUsernamePort.getAuthenticatedUserByUsername(input.followsUserUsername)
        return IsFollowingPayload(User(following.username, following.givenName, following.familyName), User(followed.username, followed.givenName, followed.familyName), isFollowingPort.isFollowing(following, followed))
    }
}