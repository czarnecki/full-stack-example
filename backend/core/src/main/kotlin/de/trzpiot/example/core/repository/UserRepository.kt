package de.trzpiot.example.core.repository

import de.trzpiot.example.core.domain.BoardResult
import de.trzpiot.example.core.domain.TimelineResult
import de.trzpiot.example.core.domain.UserEntity
import org.springframework.data.neo4j.annotation.Query
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
internal interface UserRepository : Neo4jRepository<UserEntity, Long> {
    @Query("MATCH (user:UserEntity)-[:HAS_WRITTEN]->(post:PostEntity) " +
            "WHERE ID(user) = {id} " +
            "RETURN user, post " +
            "UNION " +
            "MATCH (follower)-[:FOLLOWS]->(user)-[:HAS_WRITTEN]->(post) " +
            "WHERE ID(follower) = {id} " +
            "RETURN user, post ")
    fun getTimeline(@Param("id") id: Long): List<TimelineResult>

    @Query("MATCH (user:UserEntity) " +
            "WHERE ID(user) = {id} " +
            "MATCH (other:UserEntity) " +
            "WHERE other <> user " +
            "RETURN other AS user, " +
            "CASE WHEN (user)-[]->(other) " +
            "THEN true " +
            "ELSE false " +
            "END AS isFollowing")
    fun getBoard(@Param("id") id: Long): List<BoardResult>

    fun findBySubject(subject: String): Optional<UserEntity>
}