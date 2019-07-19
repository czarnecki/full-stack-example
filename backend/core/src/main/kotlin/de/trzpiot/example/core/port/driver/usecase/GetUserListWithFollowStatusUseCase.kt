package de.trzpiot.example.core.port.driver.usecase

import de.trzpiot.example.core.port.driver.payload.GetUserListWithFollowStatusPayload

interface GetUserListWithFollowStatusUseCase {
    fun getUserListWithFollowStatus(): GetUserListWithFollowStatusPayload
}