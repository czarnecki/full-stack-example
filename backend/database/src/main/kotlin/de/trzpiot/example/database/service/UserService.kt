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
        GetTimelineFromUserPort,
        GetUserListWithFollowStatusPort,
        PostMessagePort {
    override fun postMessage(user: User, message: String): Post {
        val user = UserNode(user.id, user.username)
        var post = PostNode(message = message, creationDate = Date())
        user.posts.add(post)
        post = userRepository.save(user).posts[0]
        return Post(post.id!!, post.message, post.creationDate)
    }

    override fun getUserListWithFollowStatus(user: User): List<UserListItem> {
        val userListItems = arrayListOf<UserListItem>()

        userRepository.getUserListWithFollowStatus(user.id).forEach {
            userListItems.add(UserListItem(User(it.user.id!!, it.user.username), it.isFollowing))
        }

        return userListItems
    }

    override fun getTimelineFromUser(user: User): List<TimelineItem> {
        val timelineItems = arrayListOf<TimelineItem>()

        userRepository.getTimelineFromUser(user.id).forEach {
            timelineItems.add(TimelineItem(Post(it.post.id!!, it.post.message, it.post.creationDate), User(it.user.id!!, it.user.username)))
        }

        return timelineItems.sortedByDescending { it.post.creationDate }
    }

    override fun getUserById(id: Long): User {
        val user = userRepository.findById(id)

        if (user.isPresent) {
            return User(user.get().id!!, user.get().username)
        } else {
            throw UserNotFoundException("UserNode with ID \"$id\" not found")
        }
    }

    override fun createUser(username: String): User {
        val user = userRepository.save(UserNode(username = username))
        return User(user.id!!, user.username)
    }
}