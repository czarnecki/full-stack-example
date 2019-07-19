package de.trzpiot.example.core.port.driver.usecase

import de.trzpiot.example.core.port.driver.payload.GetTimelineFromUserPayload

interface GetTimelineFromUserUseCase {
    fun getTimelineFromUser(): GetTimelineFromUserPayload
}