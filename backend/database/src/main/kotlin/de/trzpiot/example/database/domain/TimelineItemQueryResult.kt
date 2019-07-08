package de.trzpiot.example.database.domain

import org.springframework.data.neo4j.annotation.QueryResult

@QueryResult
internal data class TimelineItemQueryResult(
        var post: PostNode,
        var user: UserNode
)