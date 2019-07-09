package de.trzpiot.example.api

import com.oembedler.moon.graphql.boot.error.ThrowableGraphQLError
import graphql.GraphQLError
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler

@Component
internal class ErrorConfiguration {
    @ExceptionHandler
    fun handle(exception: RuntimeException): GraphQLError {
        return ThrowableGraphQLError(exception)
    }
}