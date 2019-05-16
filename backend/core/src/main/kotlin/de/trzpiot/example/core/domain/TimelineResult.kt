package de.trzpiot.example.core.domain

import org.springframework.data.neo4j.annotation.QueryResult

@QueryResult
internal data class TimelineResult(
        var user: UserEntity? = null,
        var post: PostEntity? = null
)