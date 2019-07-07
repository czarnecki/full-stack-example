package de.trzpiot.example.database.repository

import de.trzpiot.example.database.domain.TimelineItem
import de.trzpiot.example.database.domain.User
import de.trzpiot.example.database.domain.UserListItem
import org.springframework.data.neo4j.annotation.Query
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
internal interface UserRepository : Neo4jRepository<User, Long> {
    @Query("MATCH (u1:User)-[:HAS_WRITTEN]->(p1:Post) " +
            "WHERE ID(u1) = {userId}" +
            "RETURN u1 AS user, p1 AS post " +
            "UNION " +
            "MATCH (u1:User)-[:IS_FOLLOWING]->(u2:User)-[:HAS_WRITTEN]->(p1:Post) " +
            "WHERE ID(u1) = {userId} " +
            "RETURN u2 AS user, p1 AS post")
    fun getTimelineFromUser(@Param("userId") userId: Long): List<TimelineItem>

    @Query("MATCH (u1:User) WHERE ID(u1) = {userId} " +
            "MATCH (u2:User) WHERE u1 <> u2 " +
            "RETURN u2 as user, " +
            "CASE WHEN (u1)-[:IS_FOLLOWING]->(u2) " +
            "THEN true ELSE false " +
            "END AS isFollowing " +
            "ORDER BY user.username")
    fun getUserListWithFollowStatus(@Param("userId") userId: Long): List<UserListItem>
}