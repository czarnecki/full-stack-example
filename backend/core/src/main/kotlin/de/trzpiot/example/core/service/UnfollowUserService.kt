package de.trzpiot.example.core.service

import de.trzpiot.example.core.NotFollowingException
import de.trzpiot.example.core.domain.User
import de.trzpiot.example.core.port.driven.GetAuthenticatedUserByUsernamePort
import de.trzpiot.example.core.port.driven.GetCurrentlyLoggedInUserPort
import de.trzpiot.example.core.port.driven.IsFollowingPort
import de.trzpiot.example.core.port.driven.UnfollowUserPort
import de.trzpiot.example.core.port.driver.input.UnfollowUserInput
import de.trzpiot.example.core.port.driver.payload.UnfollowUserPayload
import de.trzpiot.example.core.port.driver.usecase.UnfollowUserUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class UnfollowUserService
@Autowired
constructor(private val getCurrentlyLoggedInUserPort: GetCurrentlyLoggedInUserPort,
            private val getAuthenticatedUserByUsernamePort: GetAuthenticatedUserByUsernamePort,
            private val isFollowingPort: IsFollowingPort,
            private val unfollowUserPort: UnfollowUserPort) : UnfollowUserUseCase {
    override fun unfollowUser(input: UnfollowUserInput): UnfollowUserPayload {
        val currentlyLoggedInUserUsername = getCurrentlyLoggedInUserPort.getCurrentlyLoggedInUser().username
        val following = getAuthenticatedUserByUsernamePort.getAuthenticatedUserByUsername(currentlyLoggedInUserUsername)
        val followed = getAuthenticatedUserByUsernamePort.getAuthenticatedUserByUsername(input.followedUserUsername)

        if (!isFollowingPort.isFollowing(following, followed))
            throw NotFollowingException("Can't unfollow, because user with ID \"${following.id}\" isn't following user with ID \"${followed.id}\".")

        unfollowUserPort.unfollowUser(following, followed)
        return UnfollowUserPayload(User(following.username, following.givenName, following.familyName), User(followed.username, followed.givenName, followed.familyName))
    }
}