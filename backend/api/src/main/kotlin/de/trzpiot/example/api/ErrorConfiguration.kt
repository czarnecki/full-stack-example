package de.trzpiot.example.api

import com.oembedler.moon.graphql.boot.error.ThrowableGraphQLError
import de.trzpiot.example.core.AlreadyFollowingException
import de.trzpiot.example.core.SelfFollowException
import graphql.GraphQLError
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.RuntimeException

@Component
internal class ErrorConfiguration {
    @ExceptionHandler
    fun handle(exception: RuntimeException): GraphQLError {
        return ThrowableGraphQLError(exception)
    }
}