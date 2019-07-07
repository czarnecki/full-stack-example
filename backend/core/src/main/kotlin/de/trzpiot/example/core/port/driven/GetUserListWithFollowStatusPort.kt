package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.User
import de.trzpiot.example.core.domain.UserListItem

interface GetUserListWithFollowStatusPort {
    fun getUserListWithFollowStatus(user: User): List<UserListItem>
}