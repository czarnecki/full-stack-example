package de.trzpiot.example.security

import de.trzpiot.example.core.domain.User
import de.trzpiot.example.core.port.driven.CreateUserPort
import de.trzpiot.example.core.port.driven.GetCurrentlyLoggedInUserPort
import de.trzpiot.example.core.port.driven.IsUserWithUsernamePresentPort
import org.keycloak.KeycloakPrincipal
import org.keycloak.representations.AccessToken
import org.springframework.context.event.EventListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
internal class SecurityService(private val isUserWithUsernamePresentPort: IsUserWithUsernamePresentPort,
                               private val createUserPort: CreateUserPort) : GetCurrentlyLoggedInUserPort {
    override fun getCurrentlyLoggedInUser(): User {
        val username = getToken().preferredUsername
        val givenName = getToken().givenName
        val familyName = getToken().familyName
        return User(username = username, givenName = givenName, familyName = familyName)
    }

    private fun getToken(): AccessToken {
        val authentication = SecurityContextHolder.getContext().authentication
        return KeycloakPrincipal::class.java.cast(authentication.principal).keycloakSecurityContext.token
    }

    @EventListener
    fun registerUserAtFirstLogin(authenticationSuccessEvent: AuthenticationSuccessEvent) {
        val user = getCurrentlyLoggedInUser()

        if (!isUserWithUsernamePresentPort.isUserWithUsernamePresent(user.username)) {
            createUserPort.createUser(user.username, user.givenName, user.familyName)
        }
    }
}