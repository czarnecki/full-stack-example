package de.trzpiot.example.database.domain

import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.Relationship.OUTGOING

@NodeEntity
internal data class UserNode(
        var id: Long? = null,
        var username: String,
        var givenName: String,
        var familyName: String,

        @Relationship(direction = OUTGOING)
        var follows: MutableList<FollowRelationship> = mutableListOf(),

        @Relationship(value = "HAS_WRITTEN", direction = OUTGOING)
        var posts: MutableList<PostNode> = mutableListOf()
)