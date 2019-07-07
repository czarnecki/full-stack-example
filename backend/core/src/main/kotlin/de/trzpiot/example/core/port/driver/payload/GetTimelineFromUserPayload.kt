package de.trzpiot.example.core.port.driver.payload

import de.trzpiot.example.core.domain.TimelineItem

data class GetTimelineFromUserPayload(val userId: Long, val posts: List<TimelineItem>)