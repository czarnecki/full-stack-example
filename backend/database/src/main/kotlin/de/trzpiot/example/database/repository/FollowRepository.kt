package de.trzpiot.example.database.repository

import de.trzpiot.example.database.domain.Follow
import org.springframework.data.neo4j.annotation.Query
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
internal interface FollowRepository : Neo4jRepository<Follow, Long> {
    @Query("MATCH (u1:User)-[r:IS_FOLLOWING]->(u2:User) WHERE ID(u1) = {followingUserId} AND ID(u2) = {followedUserId} " +
            "RETURN CASE WHEN r IS NOT NULL THEN true ELSE false END AS isFollowing")
    fun isFollowing(@Param("followingUserId") followingUserId: Long, @Param("followedUserId") followedUserId: Long): Boolean

    @Query("MATCH (u1:User)-[r:IS_FOLLOWING]->(u2:User) WHERE ID(u1) = {followingUserId} AND ID(u2) = {followedUserId} RETURN u1, u2, r")
    fun findRelationshipBetweenTwoUsers(@Param("followingUserId") followingUserId: Long, @Param("followedUserId") followedUserId: Long): Follow
}