package de.trzpiot.example.database.service

import de.trzpiot.example.core.UserNotFoundException
import de.trzpiot.example.core.domain.Post
import de.trzpiot.example.core.domain.TimelineItem
import de.trzpiot.example.core.domain.User
import de.trzpiot.example.core.domain.UserListItem
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
        GetUserByIdPort,
        GetUserByUsernamePort,
        GetTimelineFromUserPort,
        GetUserListWithFollowStatusPort,
        PostMessagePort,
        IsUserWithUsernamePresentPort {
    override fun isUserWithUsernamePresent(username: String): Boolean {
        return userRepository.findByUsername(username).isPresent
    }

    override fun postMessage(user: User, message: String): Post {
        val userNode = UserNode(user.id, user.username, user.firstName, user.lastName)
        var post = PostNode(message = message, creationDate = Date())
        userNode.posts.add(post)
        post = userRepository.save(userNode).posts[0]
        return Post(post.id!!, post.message, post.creationDate)
    }

    override fun getUserListWithFollowStatus(user: User): List<UserListItem> {
        val userListItems = arrayListOf<UserListItem>()

        userRepository.getUserListWithFollowStatus(user.id).forEach {
            userListItems.add(UserListItem(User(it.user.id!!, it.user.username, it.user.firstName, it.user.lastName), it.isFollowing))
        }

        return userListItems
    }

    override fun getTimelineFromUser(user: User): List<TimelineItem> {
        val timelineItems = arrayListOf<TimelineItem>()

        userRepository.getTimelineFromUser(user.id).forEach {
            timelineItems.add(TimelineItem(Post(it.post.id!!, it.post.message, it.post.creationDate), User(it.user.id!!, it.user.username, it.user.firstName, it.user.lastName)))
        }

        return timelineItems.sortedByDescending { it.post.creationDate }
    }

    override fun getUserById(id: Long): User {
        val user = userRepository.findById(id)

        if (user.isPresent) {
            return User(user.get().id!!, user.get().username, user.get().firstName, user.get().lastName)
        } else {
            throw UserNotFoundException("User with ID \"$id\" not found")
        }
    }

    override fun getUserByUsername(username: String): User {
        val user = userRepository.findByUsername(username)

        if (user.isPresent) {
            return User(user.get().id!!, user.get().username, user.get().firstName, user.get().lastName)
        } else {
            throw UserNotFoundException("User with username \"$username\" not found")
        }
    }

    override fun createUser(username: String, firstName: String, lastName: String): User {
        val user = userRepository.save(UserNode(username = username, firstName = firstName, lastName = lastName))
        return User(user.id!!, user.username, user.firstName, user.lastName)
    }
}