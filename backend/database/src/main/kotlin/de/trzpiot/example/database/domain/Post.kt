package de.trzpiot.example.database.domain

import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.typeconversion.DateLong
import java.util.*

@NodeEntity
internal data class Post(
        var id: Long? = null,
        var message: String,

        @DateLong
        var creationDate: Date
)