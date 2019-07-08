package de.trzpiot.example.database.domain

import org.springframework.data.neo4j.annotation.QueryResult

@QueryResult
internal data class UserListItemQueryResult(
        var user: UserNode,
        var isFollowing: Boolean
)