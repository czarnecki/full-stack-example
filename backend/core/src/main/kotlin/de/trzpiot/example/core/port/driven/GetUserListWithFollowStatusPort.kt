package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.AuthenticatedUser
import de.trzpiot.example.core.domain.UserListItem

interface GetUserListWithFollowStatusPort {
    fun getUserListWithFollowStatus(user: AuthenticatedUser): List<UserListItem>
}