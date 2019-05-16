package de.trzpiot.example.core.config

import org.modelmapper.ModelMapper
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories

@Configuration
@EntityScan(basePackages = ["de.trzpiot.example.core.domain"])
@EnableNeo4jRepositories(basePackages = ["de.trzpiot.example.core.repository"])
class Neo4jConfig {
    @Bean
    internal fun modelMapper(): ModelMapper {
        return ModelMapper()
    }
}