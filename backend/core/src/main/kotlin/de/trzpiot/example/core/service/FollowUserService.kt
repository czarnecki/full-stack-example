package de.trzpiot.example.core.service

import de.trzpiot.example.core.AlreadyFollowingException
import de.trzpiot.example.core.domain.User
import de.trzpiot.example.core.port.driven.FollowUserPort
import de.trzpiot.example.core.port.driven.GetAuthenticatedUserByUsernamePort
import de.trzpiot.example.core.port.driven.GetCurrentlyLoggedInUserPort
import de.trzpiot.example.core.port.driven.IsFollowingPort
import de.trzpiot.example.core.port.driver.input.FollowUserInput
import de.trzpiot.example.core.port.driver.payload.FollowUserPayload
import de.trzpiot.example.core.port.driver.usecase.FollowUserUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class FollowUserService
@Autowired
constructor(
        private val getCurrentlyLoggedInUserPort: GetCurrentlyLoggedInUserPort,
        private val getAuthenticatedUserByUsernamePort: GetAuthenticatedUserByUsernamePort,
        private val followUserPort: FollowUserPort,
        private val isFollowingPort: IsFollowingPort) : FollowUserUseCase {
    override fun followUser(input: FollowUserInput): FollowUserPayload {
        val currentlyLoggedInUserUsername = getCurrentlyLoggedInUserPort.getCurrentlyLoggedInUser().username
        val following = getAuthenticatedUserByUsernamePort.getAuthenticatedUserByUsername(currentlyLoggedInUserUsername)
        val followed = getAuthenticatedUserByUsernamePort.getAuthenticatedUserByUsername(input.followedUserUsername)

        if (isFollowingPort.isFollowing(following, followed))
            throw AlreadyFollowingException("User with ID \"${following.id}\" is already following user with ID \"${followed.id}\".")

        followUserPort.followUser(following, followed)
        return FollowUserPayload(User(following.username, following.givenName, following.familyName), User(followed.username, followed.givenName, followed.familyName), true)
    }
}