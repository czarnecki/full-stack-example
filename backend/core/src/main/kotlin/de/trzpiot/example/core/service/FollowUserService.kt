package de.trzpiot.example.core.service

import de.trzpiot.example.core.AlreadyFollowingException
import de.trzpiot.example.core.SelfFollowException
import de.trzpiot.example.core.port.driven.FollowUserPort
import de.trzpiot.example.core.port.driven.GetUserByIdPort
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
        private val getUserByIdPort: GetUserByIdPort,
        private val followUserPort: FollowUserPort,
        private val isFollowingPort: IsFollowingPort) : FollowUserUseCase {
    override fun followUser(input: FollowUserInput): FollowUserPayload {
        if (input.followingUserId == input.followedUserId)
            throw SelfFollowException("User with ID \"${input.followingUserId}\" can't follow himself.")

        val following = getUserByIdPort.getUserById(input.followingUserId)
        val followed = getUserByIdPort.getUserById(input.followedUserId)

        if (isFollowingPort.isFollowing(following, followed))
            throw AlreadyFollowingException("User with ID \"${following.id}\" is already following user with ID \"${followed.id}\".")

        followUserPort.followUser(following, followed)
        return FollowUserPayload(following, followed)
    }
}