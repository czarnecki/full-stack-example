package de.trzpiot.example.database.domain

import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.Relationship.OUTGOING

@NodeEntity
internal data class User(
        var id: Long? = null,
        var username: String,

        @Relationship(direction = OUTGOING)
        var follows: MutableList<Follow> = mutableListOf(),

        @Relationship(value = "HAS_WRITTEN", direction = OUTGOING)
        var posts: MutableList<Post> = mutableListOf()
)