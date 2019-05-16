package de.trzpiot.example.core.repository

import de.trzpiot.example.core.domain.FollowRelationship
import org.springframework.data.neo4j.annotation.Query
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
internal interface FollowRelationshipRepository : Neo4jRepository<FollowRelationship, Long> {
    @Query("MATCH (follower:UserEntity)-[r:FOLLOWS]->(follows:UserEntity) " +
            "WHERE ID(follower) = {followerId} AND ID(follows) = {followsId} " +
            "RETURN follower, follows, r")
    fun findFollowRelationship(@Param("followerId") followerId: Long, @Param("followsId") followsId:
    Long): FollowRelationship?
}