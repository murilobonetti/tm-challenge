package com.mbonetti.tmchallenge.repository

import androidx.lifecycle.LiveData
import com.mbonetti.tmchallenge.api.EventsResponse
import com.mbonetti.tmchallenge.db.models.Event
import retrofit2.Response

interface EventRepository {

    suspend fun getEvents(pageNumber: Int) : Response<EventsResponse>

    suspend fun getEventsByKeyword(keyword: String, pageNumber: Int) : Response<EventsResponse>

    suspend fun getEventsByCity(city: String, pageNumber: Int) : Response<EventsResponse>

    suspend fun insertOrUpdate(events: List<Event>)

    fun getSavedEvents() : LiveData<List<Event>>
}
