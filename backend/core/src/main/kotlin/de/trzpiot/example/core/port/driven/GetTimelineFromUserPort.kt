package de.trzpiot.example.core.port.driven

import de.trzpiot.example.core.domain.TimelineItem
import de.trzpiot.example.core.domain.User

interface GetTimelineFromUserPort {
    fun getTimelineFromUser(user: User): List<TimelineItem>
}