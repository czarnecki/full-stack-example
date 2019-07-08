package de.trzpiot.example.core.port.driver.payload

import de.trzpiot.example.core.domain.User
import de.trzpiot.example.core.domain.UserListItem

data class GetUserListWithFollowStatusPayload(val user: User, val otherUsersWithFollowStatus: List<UserListItem>)