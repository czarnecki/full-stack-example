package de.trzpiot.example.core.domain

import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.typeconversion.DateLong
import java.util.*

@NodeEntity
internal data class PostEntity(
        var id: Long? = null,
        var message: String? = null,
        @DateLong
        var creationDate: Date = Date()
)