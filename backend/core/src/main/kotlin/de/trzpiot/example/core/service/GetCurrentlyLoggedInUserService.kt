package de.trzpiot.example.core.service

import de.trzpiot.example.core.port.driven.GetCurrentlyLoggedInUserPort
import de.trzpiot.example.core.port.driver.payload.GetCurrentlyLoggedInUserPayload
import de.trzpiot.example.core.port.driver.usecase.GetCurrentlyLoggedInUserUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class GetCurrentlyLoggedInUserService
@Autowired
constructor(private val getCurrentlyLoggedInUserPort: GetCurrentlyLoggedInUserPort) : GetCurrentlyLoggedInUserUseCase {
    override fun getCurrentlyLoggedInUser(): GetCurrentlyLoggedInUserPayload {
        return GetCurrentlyLoggedInUserPayload(getCurrentlyLoggedInUserPort.getCurrentlyLoggedInUser())
    }
}