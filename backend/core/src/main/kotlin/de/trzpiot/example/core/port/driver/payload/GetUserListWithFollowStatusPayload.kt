package de.trzpiot.example.core.port.driver.payload

import de.trzpiot.example.core.domain.UserListItem

data class GetUserListWithFollowStatusPayload(val userId: Long, val otherUsersWithFollowStatus: List<UserListItem>)