package de.trzpiot.example.core.domain

import org.neo4j.ogm.annotation.EndNode
import org.neo4j.ogm.annotation.RelationshipEntity
import org.neo4j.ogm.annotation.StartNode
import org.neo4j.ogm.annotation.typeconversion.DateLong
import java.util.*

@RelationshipEntity(type = "FOLLOWS")
internal data class FollowRelationship(
        var id: Long? = null,
        @DateLong
        var dateOfFollowing: Date = Date()
) {
    @StartNode
    var follower: UserEntity? = null

    @EndNode
    var follows: UserEntity? = null
}
