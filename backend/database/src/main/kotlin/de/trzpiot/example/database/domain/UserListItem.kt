package de.trzpiot.example.database.domain

import org.springframework.data.neo4j.annotation.QueryResult

@QueryResult
internal data class UserListItem(
        var user: User,
        var isFollowing: Boolean
)