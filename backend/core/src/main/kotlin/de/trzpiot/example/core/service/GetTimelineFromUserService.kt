package de.trzpiot.example.core.service

import de.trzpiot.example.core.port.driven.GetTimelineFromUserPort
import de.trzpiot.example.core.port.driven.GetUserByIdPort
import de.trzpiot.example.core.port.driver.input.GetTimelineFromUserInput
import de.trzpiot.example.core.port.driver.payload.GetTimelineFromUserPayload
import de.trzpiot.example.core.port.driver.usecase.GetTimelineFromUserUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class GetTimelineFromUserService
@Autowired
constructor(private val getUserByIdPort: GetUserByIdPort,
            private val getTimelineFromUserPort: GetTimelineFromUserPort) : GetTimelineFromUserUseCase {
    override fun getTimelineFromUser(getTimelineFromUserInput: GetTimelineFromUserInput): GetTimelineFromUserPayload {
        return GetTimelineFromUserPayload(getTimelineFromUserInput.userId,
                getTimelineFromUserPort.getTimelineFromUser(getUserByIdPort.getUserById(getTimelineFromUserInput.userId)))
    }
}