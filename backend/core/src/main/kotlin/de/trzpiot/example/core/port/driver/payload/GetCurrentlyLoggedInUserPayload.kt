package de.trzpiot.example.core.port.driver.payload

import de.trzpiot.example.core.domain.User

data class GetCurrentlyLoggedInUserPayload(val user: User)