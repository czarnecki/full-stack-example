package de.trzpiot.example.core.port.driver.usecase

import de.trzpiot.example.core.port.driver.input.GetUserListWithFollowStatusInput
import de.trzpiot.example.core.port.driver.payload.GetUserListWithFollowStatusPayload

interface GetUserListWithFollowStatusUseCase {
    fun getUserListWithFollowStatus(getUserListWithFollowStatusInput: GetUserListWithFollowStatusInput): GetUserListWithFollowStatusPayload
}