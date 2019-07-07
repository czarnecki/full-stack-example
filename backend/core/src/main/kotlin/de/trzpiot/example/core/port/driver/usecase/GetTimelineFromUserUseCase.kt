package de.trzpiot.example.core.port.driver.usecase

import de.trzpiot.example.core.port.driver.input.GetTimelineFromUserInput
import de.trzpiot.example.core.port.driver.payload.GetTimelineFromUserPayload

interface GetTimelineFromUserUseCase {
    fun getTimelineFromUser(getTimelineFromUserInput: GetTimelineFromUserInput): GetTimelineFromUserPayload
}