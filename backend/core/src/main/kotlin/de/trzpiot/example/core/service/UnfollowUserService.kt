package de.trzpiot.example.core.service

import de.trzpiot.example.core.NotFollowingException
import de.trzpiot.example.core.port.driven.GetUserByIdPort
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
constructor(
        private val getUserByIdPort: GetUserByIdPort,
        private val unfollowUserPort: UnfollowUserPort,
        private val isFollowingPort: IsFollowingPort) : UnfollowUserUseCase {
    override fun unfollowUser(input: UnfollowUserInput): UnfollowUserPayload {
        val following = getUserByIdPort.getUserById(input.followingUserId)
        val followed = getUserByIdPort.getUserById(input.followedUserId)

        if (!isFollowingPort.isFollowing(following, followed))
            throw NotFollowingException("Can't unfollow, because user with ID \"${following.id}\" isn't following user with ID \"${followed.id}\".")

        unfollowUserPort.unfollowUser(following, followed)
        return UnfollowUserPayload(following, followed)
    }
}