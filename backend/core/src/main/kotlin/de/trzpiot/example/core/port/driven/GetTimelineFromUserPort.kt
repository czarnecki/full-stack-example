package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.AuthenticatedUser
import de.trzpiot.example.core.domain.TimelineItem

interface GetTimelineFromUserPort {
    fun getTimelineFromUser(user: AuthenticatedUser): List<TimelineItem>
}