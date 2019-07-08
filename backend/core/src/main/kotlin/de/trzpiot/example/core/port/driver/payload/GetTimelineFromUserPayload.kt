package de.trzpiot.example.core.port.driver.payload

import de.trzpiot.example.core.domain.TimelineItem
import de.trzpiot.example.core.domain.User

data class GetTimelineFromUserPayload(val user: User, val timeline: List<TimelineItem>)