package de.trzpiot.example.core.service

import de.trzpiot.example.core.port.driven.GetUserByIdPort
import de.trzpiot.example.core.port.driven.IsFollowingPort
import de.trzpiot.example.core.port.driver.input.IsFollowingInput
import de.trzpiot.example.core.port.driver.payload.IsFollowingPayload
import de.trzpiot.example.core.port.driver.usecase.IsFollowingUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class IsFollowingService
@Autowired
constructor(private val getUserByIdPort: GetUserByIdPort, private val isFollowingPort: IsFollowingPort) : IsFollowingUseCase {
    override fun isFollowing(input: IsFollowingInput): IsFollowingPayload {
        val following = getUserByIdPort.getUserById(input.followerUserId)
        val followed = getUserByIdPort.getUserById(input.followsUserId)
        return IsFollowingPayload(following, followed, isFollowingPort.isFollowing(following, followed))
    }
}