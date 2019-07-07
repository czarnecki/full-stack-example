package de.trzpiot.example.database.domain

import org.neo4j.ogm.annotation.EndNode
import org.neo4j.ogm.annotation.RelationshipEntity
import org.neo4j.ogm.annotation.StartNode
import org.neo4j.ogm.annotation.typeconversion.DateLong
import java.util.*


@RelationshipEntity(type = "IS_FOLLOWING")
internal data class Follow(
        var id: Long? = null,

        @DateLong
        var dateOfFollowing: Date,

        @StartNode
        var follower: User,

        @EndNode
        var follows: User
)