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
    override fun postMessage(author: User, message: String): Long {
        val user = UserNode(author.id, author.username)
        val post = PostNode(message = message, creationDate = Date())
        user.posts.add(post)
        return userRepository.save(user).posts[0].id
                ?: throw KotlinNullPointerException("Something went wrong saving the post. The ID from the returned post is null.")
    }

    override fun getUserListWithFollowStatus(user: User): List<UserListItem> {
        val userListWithFollowStatus = userRepository.getUserListWithFollowStatus(user.id)

        val userListItems = arrayListOf<UserListItem>()

        userListWithFollowStatus.forEach {
            val userId = it.user.id
                    ?: throw KotlinNullPointerException("Something went wrong fetching user list with follow status. The ID from a returned user is null.")
            val userFromCoreModule = User(userId, it.user.username)
            userListItems.add(UserListItem(userFromCoreModule, it.isFollowing))
        }

        return userListItems
    }

    override fun getTimelineFromUser(user: User): List<TimelineItem> {
        val posts = userRepository.getTimelineFromUser(user.id)

        val timelineItems = arrayListOf<TimelineItem>()

        posts.forEach {
            val postId = it.post.id
                    ?: throw KotlinNullPointerException("Something went wrong fetching timeline from an user. The ID from a returned post is null.")
            val userId = it.user.id
                    ?: throw KotlinNullPointerException("Something went wrong fetching timeline from an user. The ID from a returned user is null.")
            val postFromCoreModule = Post(postId, it.post.message, it.post.creationDate)
            val userFromCoreModule = User(userId, it.user.username)
            timelineItems.add(TimelineItem(postFromCoreModule, userFromCoreModule))
        }

        return timelineItems.sortedByDescending { it.post.creationDate }
    }

    override fun getUserById(id: Long): User {
        val userFromDatabase = userRepository.findById(id)

        if (userFromDatabase.isPresent) {
            val userId = userFromDatabase.get().id
                    ?: throw KotlinNullPointerException("Something went wrong fetching an user. The ID from the returned user is null.")
            return User(userId, userFromDatabase.get().username)
        } else {
            throw UserNotFoundException("UserNode with ID \"$id\" not found")
        }
    }

    override fun createUser(username: String): Long {
        val userNode = UserNode(username = username)
        return userRepository.save(userNode).id
                ?: throw KotlinNullPointerException("Something went wrong saving the user. The ID from the returned user is null.")
    }
}