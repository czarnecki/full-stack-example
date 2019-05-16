package de.trzpiot.example.core.service

import de.trzpiot.example.core.SelfFollowException
import de.trzpiot.example.core.api.*
import de.trzpiot.example.core.api.UserService
import de.trzpiot.example.core.domain.FollowRelationship
import de.trzpiot.example.core.domain.PostEntity
import de.trzpiot.example.core.domain.UserEntity
import de.trzpiot.example.core.repository.FollowRelationshipRepository
import de.trzpiot.example.core.repository.UserRepository
import org.keycloak.KeycloakPrincipal
import org.keycloak.representations.AccessToken
import org.modelmapper.ModelMapper
import org.modelmapper.TypeToken
import org.neo4j.ogm.exception.core.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.lang.reflect.Type
import java.util.*

@Service
internal class UserService
@Autowired
constructor(private val userRepository: UserRepository,
            private val followRelationshipRepository: FollowRelationshipRepository,
            private val modelMapper: ModelMapper) : UserService {

    val timelineList: Type = object : TypeToken<List<Timeline>>() {}.type
    val boardList: Type = object : TypeToken<List<Board>>() {}.type

    override fun getCurrentUser(): User {
        val subject = getToken()?.subject ?: throw IllegalArgumentException("Subject excepted")
        val username = getToken()?.preferredUsername ?: throw IllegalArgumentException("Username " +
                "excepted")

        val user = userRepository.findBySubject(subject)
        return if (!user.isPresent) {
            val user = createUser(username, subject)
            modelMapper.map(user, User::class.java)
        } else {
            modelMapper.map(user.get(), User::class.java)
        }
    }

    override fun followUser(follower: User, follows: User) {
        val followerEntity = modelMapper.map(follower, UserEntity::class.java)
        val followsEntity = modelMapper.map(follows, UserEntity::class.java)

        val followerId = followerEntity.id ?: throw IllegalArgumentException("ID excepted")
        val followsId = followsEntity.id ?: throw IllegalArgumentException("ID excepted")

        if (followerEntity == followsEntity)
            throw SelfFollowException("User ${followerEntity.subject} can't follow himself")

        val followRelationship = followRelationshipRepository.findFollowRelationship(followerId,
                followsId)

        if (followRelationship != null)
            followRelationshipRepository.delete(followRelationship)
        else
            createFollowRelationship(followerEntity, followsEntity)
    }

    override fun post(author: User, post: Post) {
        val postEntity = modelMapper.map(post, PostEntity::class.java)
        val userEntity = modelMapper.map(author, UserEntity::class.java)

        postEntity.creationDate = Date()

        userEntity.posts?.add(postEntity)
        userRepository.save(userEntity)
    }

    override fun findUser(id: Long): User {
        val userEntity = userRepository.findById(id, 1)

        return if (userEntity.isPresent)
            modelMapper.map(userEntity.get(), User::class.java)
        else
            throw NotFoundException("User not found")
    }

    override fun getTimeline(user: User): List<Timeline> {
        val id: Long = user.id ?: throw IllegalArgumentException("ID excepted")
        var timeline = userRepository.getTimeline(id)
        timeline = timeline.sortedByDescending { it.post?.creationDate }
        return modelMapper.map(timeline, timelineList)
    }

    override fun getBoard(user: User): List<Board> {
        val id: Long = user.id ?: throw IllegalArgumentException("ID excepted")
        var board = userRepository.getBoard(id)
        board = board.sortedBy { it.user?.username }
        return modelMapper.map(board, boardList)
    }

    private fun createUser(username: String, subject: String): UserEntity {
        val newUser = UserEntity()
        newUser.username = username
        newUser.subject = subject
        return userRepository.save(newUser)
    }

    private fun createFollowRelationship(followerEntity: UserEntity, followsEntity: UserEntity) {
        val followRelationship = FollowRelationship()
        followRelationship.dateOfFollowing = Date()
        followRelationship.follower = followerEntity
        followRelationship.follows = followsEntity
        followerEntity.follows?.add(followRelationship)
        userRepository.save(followerEntity)
    }

    private fun getToken(): AccessToken? {
        val authentication = SecurityContextHolder.getContext().authentication

        if (authentication != null) {
            val principal = authentication.principal

            if (principal is KeycloakPrincipal<*>) {
                return KeycloakPrincipal::class.java.cast(principal).keycloakSecurityContext.token
            }
        }
        return null
    }
}