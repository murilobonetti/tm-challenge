package com.mbonetti.tmchallenge.repository

import com.mbonetti.tmchallenge.api.EventsApi
import com.mbonetti.tmchallenge.db.EventDatabase
import com.mbonetti.tmchallenge.db.models.Event
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val database: EventDatabase,
    private val api: EventsApi
) : EventRepository {

    override suspend fun getEvents(pageNumber: Int) =
        api.getEvents(pageNumber = pageNumber)

    override suspend fun getEventsByKeyword(keyword: String, pageNumber: Int) =
        api.getEventsByKeyword(searchKeyword = keyword, pageNumber = pageNumber)

    override suspend fun getEventsByCity(city: String, pageNumber: Int) =
        api.getEventsByCity(city = city, pageNumber = pageNumber)

    override suspend fun insertOrUpdate(events: List<Event>) = database.getEventDao().insertOrUpdate(events)

    override fun getSavedEvents() = database.getEventDao().getAllEvents()

}
