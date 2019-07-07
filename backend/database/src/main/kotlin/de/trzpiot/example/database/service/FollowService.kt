package de.trzpiot.example.database.service

import de.trzpiot.example.core.port.driven.FollowUserPort
import de.trzpiot.example.core.port.driven.IsFollowingPort
import de.trzpiot.example.core.port.driven.UnfollowUserPort
import de.trzpiot.example.database.domain.Follow
import de.trzpiot.example.database.domain.User
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
    override fun unfollowUser(following: UserFromCoreModule, followed: UserFromCoreModule): Long {
        val followRelationship = followRepository.findRelationshipBetweenTwoUsers(following.id, followed.id)
        followRepository.delete(followRelationship)
        return followRelationship.id
                ?: throw KotlinNullPointerException("Something went wrong deleting the follow relationship. The ID from the returned follow relationship is null.")
    }

    override fun isFollowing(following: UserFromCoreModule, followed: UserFromCoreModule): Boolean {
        return followRepository.isFollowing(following.id, followed.id)
    }

    override fun followUser(following: UserFromCoreModule, followed: UserFromCoreModule): Long {
        val follow = Follow(dateOfFollowing = Date(), follower = User(following.id, following.username), follows = User(followed.id, followed.username))
        return followRepository.save(follow).id
                ?: throw KotlinNullPointerException("Something went wrong saving the follow relationship. The ID from the returned follow relationship is null.")
    }
}