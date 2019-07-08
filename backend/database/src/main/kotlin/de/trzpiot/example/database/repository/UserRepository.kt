package de.trzpiot.example.database.repository

import de.trzpiot.example.database.domain.TimelineItemQueryResult
import de.trzpiot.example.database.domain.UserNode
import de.trzpiot.example.database.domain.UserListItemQueryResult
import org.springframework.data.neo4j.annotation.Query
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
internal interface UserRepository : Neo4jRepository<UserNode, Long> {
    @Query("MATCH (u1:UserNode)-[:HAS_WRITTEN]->(p1:PostNode) " +
            "WHERE ID(u1) = {userId}" +
            "RETURN u1 AS user, p1 AS post " +
            "UNION " +
            "MATCH (u1:UserNode)-[:IS_FOLLOWING]->(u2:UserNode)-[:HAS_WRITTEN]->(p1:PostNode) " +
            "WHERE ID(u1) = {userId} " +
            "RETURN u2 AS user, p1 AS post")
    fun getTimelineFromUser(@Param("userId") userId: Long): List<TimelineItemQueryResult>

    @Query("MATCH (u1:UserNode) WHERE ID(u1) = {userId} " +
            "MATCH (u2:UserNode) WHERE u1 <> u2 " +
            "RETURN u2 as user, " +
            "CASE WHEN (u1)-[:IS_FOLLOWING]->(u2) " +
            "THEN true ELSE false " +
            "END AS isFollowing " +
            "ORDER BY user.username")
    fun getUserListWithFollowStatus(@Param("userId") userId: Long): List<UserListItemQueryResult>
}