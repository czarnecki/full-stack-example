package de.trzpiot.example.database.service

import de.trzpiot.example.core.domain.AuthenticatedUser
import de.trzpiot.example.core.port.driven.FollowUserPort
import de.trzpiot.example.core.port.driven.IsFollowingPort
import de.trzpiot.example.core.port.driven.UnfollowUserPort
import de.trzpiot.example.database.domain.FollowRelationship
import de.trzpiot.example.database.domain.UserNode
import de.trzpiot.example.database.repository.FollowRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
internal class FollowService
@Autowired
constructor(private val followRepository: FollowRepository) :
        FollowUserPort,
        UnfollowUserPort,
        IsFollowingPort {
    override fun followUser(following: AuthenticatedUser, followed: AuthenticatedUser) {
        followRepository.save(FollowRelationship(dateOfFollowing = Date(), follower = UserNode(following.id, following.username, following.givenName, following.familyName), follows = UserNode(followed.id, followed.username, followed.givenName, followed.familyName)))
    }

    override fun unfollowUser(following: AuthenticatedUser, followed: AuthenticatedUser) {
        followRepository.delete(followRepository.findRelationshipBetweenTwoUsers(following.id, followed.id))
    }

    override fun isFollowing(following: AuthenticatedUser, followed: AuthenticatedUser): Boolean {
        return followRepository.isFollowing(following.id, followed.id)
    }
}