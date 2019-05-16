package de.trzpiot.example.core.domain

import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.Relationship.UNDIRECTED

@NodeEntity
internal data class UserEntity(
        var id: Long? = null,
        var subject: String? = null,
        var username: String? = null,

        @Relationship(value = "FOLLOWS", direction = UNDIRECTED)
        var follows: MutableList<FollowRelationship>? = mutableListOf(),

        @Relationship(value = "HAS_WRITTEN", direction = UNDIRECTED)
        var posts: MutableList<PostEntity>? = mutableListOf()
)