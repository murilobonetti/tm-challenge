package com.mbonetti.tmchallenge.repository

import com.mbonetti.tmchallenge.api.RetrofitInstance
import com.mbonetti.tmchallenge.db.EventDatabase
import com.mbonetti.tmchallenge.db.models.Event

class EventRepository(
    val database: EventDatabase
) {

    suspend fun getEvents(pageNumber: Int) =
        RetrofitInstance.api.getEvents(pageNumber = pageNumber)

    suspend fun getEventsByKeyword(keyword: String, pageNumber: Int) =
        RetrofitInstance.api.getEventsByKeyword(searchKeyword = keyword, pageNumber = pageNumber)

    suspend fun getEventsByCity(city: String, pageNumber: Int) =
        RetrofitInstance.api.getEventsByCity(city = city, pageNumber = pageNumber)

    suspend fun insertOrUpdate(events: List<Event>) = database.getEventDao().insertOrUpdate(events)

    fun getSavedEvents() = database.getEventDao().getAllEvents()

}
