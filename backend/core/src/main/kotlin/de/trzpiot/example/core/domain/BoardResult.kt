package de.trzpiot.example.core.domain

import jdk.nashorn.internal.objects.annotations.Property
import org.springframework.data.neo4j.annotation.QueryResult

@QueryResult
internal data class BoardResult(
        var user: UserEntity? = null,
        var isFollowing: Boolean = false
)