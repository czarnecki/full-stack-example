package de.trzpiot.example.database.service

import de.trzpiot.example.core.port.driven.FollowUserPort
import de.trzpiot.example.core.port.driven.IsFollowingPort
import de.trzpiot.example.core.port.driven.UnfollowUserPort
import de.trzpiot.example.database.domain.FollowRelationship
import de.trzpiot.example.database.domain.UserNode
import de.trzpiot.example.database.repository.FollowRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import de.trzpiot.example.core.domain.User as UserFromCoreModule

@Service
internal class FollowService
@Autowired
constructor(private val followRepository: FollowRepository) :
        FollowUserPort,
        IsFollowingPort,
        UnfollowUserPort {
    override fun unfollowUser(following: UserFromCoreModule, followed: UserFromCoreModule) {
        followRepository.delete(followRepository.findRelationshipBetweenTwoUsers(following.id, followed.id))
    }

    override fun isFollowing(following: UserFromCoreModule, followed: UserFromCoreModule): Boolean {
        return followRepository.isFollowing(following.id, followed.id)
    }

    override fun followUser(following: UserFromCoreModule, followed: UserFromCoreModule) {
        followRepository.save(FollowRelationship(dateOfFollowing = Date(), follower = UserNode(following.id, following.username), follows = UserNode(followed.id, followed.username)))
    }
}