package de.trzpiot.example.database.configuration

import org.neo4j.ogm.session.SessionFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager


@Configuration
@EnableNeo4jRepositories(basePackages = ["de.trzpiot.example.database.repository"])
@EnableConfigurationProperties(value = [Neo4jProperties::class])
internal class Neo4jConfiguration(var properties: Neo4jProperties) {
    @Bean
    @ConditionalOnMissingBean
    fun configuration(): org.neo4j.ogm.config.Configuration {
        return this.properties.createConfiguration()
    }

    @Bean
    fun sessionFactory(configuration: org.neo4j.ogm.config.Configuration): SessionFactory {
        return SessionFactory(configuration, "de.trzpiot.example.database.domain")
    }

    @Bean
    fun transactionManager(): Neo4jTransactionManager {
        return Neo4jTransactionManager(sessionFactory(configuration()))
    }
}