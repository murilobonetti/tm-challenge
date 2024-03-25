package com.mbonetti.tmchallenge

import androidx.lifecycle.LiveData
import com.mbonetti.tmchallenge.api.EventsResponse
import com.mbonetti.tmchallenge.db.EventDatabase
import com.mbonetti.tmchallenge.db.models.Event
import com.mbonetti.tmchallenge.repository.EventRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class EventRepositoryImplTest {

    private lateinit var eventRepositoryImpl: EventRepositoryImpl
    private lateinit var eventDatabase: EventDatabase

    @Before
    fun setup() {
        eventDatabase = mockk()
        eventRepositoryImpl = EventRepositoryImpl(eventDatabase)
    }

    @Test
    fun `test getEvents`() {
        val pageNumber = 1
        val expectedResponse = mockk<Response<EventsResponse>>()
        val eventRepositoryImpl = mockk<EventRepositoryImpl>() // Mock the EventRepository
        coEvery { eventRepositoryImpl.getEvents(pageNumber) } returns expectedResponse

        val result = runBlocking { eventRepositoryImpl.getEvents(pageNumber) }

        assertEquals(expectedResponse, result)
    }

    @Test
    fun `test getEventsByKeyword`() {
        val keyword = "Charlotte"
        val pageNumber = 1
        val expectedResponse = mockk<Response<EventsResponse>>()
        val eventRepositoryImpl = mockk<EventRepositoryImpl>()
        coEvery { eventRepositoryImpl.getEventsByKeyword(keyword, pageNumber) } returns expectedResponse

        val result = runBlocking { eventRepositoryImpl.getEventsByKeyword(keyword, pageNumber) }

        assertEquals(expectedResponse, result)
    }

    @Test
    fun `test getEventsByCity`() {
        val city = "Phoenix"
        val pageNumber = 1
        val expectedResponse = mockk<Response<EventsResponse>>()
        val eventRepositoryImpl = mockk<EventRepositoryImpl>()
        coEvery { eventRepositoryImpl.getEventsByCity(city, pageNumber) } returns expectedResponse

        val result = runBlocking { eventRepositoryImpl.getEventsByCity(city, pageNumber) }

        assertEquals(expectedResponse, result)
    }

    @Test
    fun `test insertOrUpdate`() {
        val events = listOf(mockk<Event>())
        coEvery { eventDatabase.getEventDao().insertOrUpdate(events) } returns Unit

        runBlocking { eventRepositoryImpl.insertOrUpdate(events) }
    }

    @Test
    fun `test getSavedEvents`() {
        val expectedEvents = mockk<LiveData<List<Event>>>(relaxed = true)
        coEvery { eventDatabase.getEventDao().getAllEvents() } returns expectedEvents

        val result = eventRepositoryImpl.getSavedEvents()

        assertEquals(expectedEvents, result)
    }
}

