package de.trzpiot.example.database.service

import de.trzpiot.example.core.UserNotFoundException
import de.trzpiot.example.core.domain.*
import de.trzpiot.example.core.port.driven.*
import de.trzpiot.example.database.domain.PostNode
import de.trzpiot.example.database.domain.UserNode
import de.trzpiot.example.database.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
internal class UserService
@Autowired
constructor(private val userRepository: UserRepository) :
        CreateUserPort,
        GetAuthenticatedUserByUsernamePort,
        GetTimelineFromUserPort,
        GetUserListWithFollowStatusPort,
        PostMessagePort,
        IsUserWithUsernamePresentPort {
    override fun getAuthenticatedUserByUsername(username: String): AuthenticatedUser {
        val user = userRepository.findByUsername(username)

        if (user.isPresent) {
            return AuthenticatedUser(user.get().id!!, user.get().username, user.get().givenName, user.get().familyName)
        } else {
            throw UserNotFoundException("User with username \"$username\" not found")
        }
    }

    override fun isUserWithUsernamePresent(username: String): Boolean {
        return userRepository.findByUsername(username).isPresent
    }

    override fun postMessage(user: AuthenticatedUser, message: String): Post {
        val userNode = UserNode(user.id, user.username, user.givenName, user.familyName)
        var post = PostNode(message = message, creationDate = Date())
        userNode.posts.add(post)
        post = userRepository.save(userNode).posts[0]
        return Post(post.id!!, post.message, post.creationDate)
    }

    override fun getUserListWithFollowStatus(user: AuthenticatedUser): List<UserListItem> {
        val userListItems = arrayListOf<UserListItem>()

        userRepository.getUserListWithFollowStatus(user.id).forEach {
            userListItems.add(UserListItem(User(it.user.username, it.user.givenName, it.user.familyName), it.isFollowing))
        }

        return userListItems
    }

    override fun getTimelineFromUser(user: AuthenticatedUser): List<TimelineItem> {
        val timelineItems = arrayListOf<TimelineItem>()

        userRepository.getTimelineFromUser(user.id).forEach {
            timelineItems.add(TimelineItem(Post(it.post.id!!, it.post.message, it.post.creationDate), User(it.user.username, it.user.givenName, it.user.familyName)))
        }

        return timelineItems.sortedByDescending { it.post.creationDate }
    }

    override fun createUser(username: String, givenName: String, familyName: String): AuthenticatedUser {
        val user = userRepository.save(UserNode(username = username, givenName = givenName, familyName = familyName))
        return AuthenticatedUser(user.id!!, user.username, user.givenName, user.familyName)
    }
}