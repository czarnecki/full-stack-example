package de.trzpiot.example.core.service

import de.trzpiot.example.core.port.driven.GetUserByIdPort
import de.trzpiot.example.core.port.driven.GetUserListWithFollowStatusPort
import de.trzpiot.example.core.port.driver.input.GetUserListWithFollowStatusInput
import de.trzpiot.example.core.port.driver.payload.GetUserListWithFollowStatusPayload
import de.trzpiot.example.core.port.driver.usecase.GetUserListWithFollowStatusUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class GetUserListWithFollowStatusService
@Autowired
constructor(private val getUserListWithFollowStatusPort: GetUserListWithFollowStatusPort,
            private val getUserByIdPort: GetUserByIdPort) : GetUserListWithFollowStatusUseCase {
    override fun getUserListWithFollowStatus(getUserListWithFollowStatusInput: GetUserListWithFollowStatusInput): GetUserListWithFollowStatusPayload {
        return GetUserListWithFollowStatusPayload(getUserListWithFollowStatusInput.userId,
                getUserListWithFollowStatusPort.getUserListWithFollowStatus(getUserByIdPort.getUserById(getUserListWithFollowStatusInput.userId)))
    }
}