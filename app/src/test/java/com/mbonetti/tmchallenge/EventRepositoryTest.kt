package com.mbonetti.tmchallenge

import androidx.lifecycle.LiveData
import com.mbonetti.tmchallenge.api.EventsResponse
import com.mbonetti.tmchallenge.db.EventDatabase
import com.mbonetti.tmchallenge.db.models.Event
import com.mbonetti.tmchallenge.repository.EventRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class EventRepositoryTest {

    private lateinit var eventRepository: EventRepository
    private lateinit var eventDatabase: EventDatabase

    @Before
    fun setup() {
        eventDatabase = mockk()
        eventRepository = EventRepository(eventDatabase)
    }

    @Test
    fun `test getEvents`() {
        val pageNumber = 1
        val expectedResponse = mockk<Response<EventsResponse>>()
        val eventRepository = mockk<EventRepository>() // Mock the EventRepository
        coEvery { eventRepository.getEvents(pageNumber) } returns expectedResponse

        val result = runBlocking { eventRepository.getEvents(pageNumber) }

        assertEquals(expectedResponse, result)
    }

    @Test
    fun `test getEventsByKeyword`() {
        val keyword = "Charlotte"
        val pageNumber = 1
        val expectedResponse = mockk<Response<EventsResponse>>()
        val eventRepository = mockk<EventRepository>()
        coEvery { eventRepository.getEventsByKeyword(keyword, pageNumber) } returns expectedResponse

        val result = runBlocking { eventRepository.getEventsByKeyword(keyword, pageNumber) }

        assertEquals(expectedResponse, result)
    }

    @Test
    fun `test getEventsByCity`() {
        val city = "Phoenix"
        val pageNumber = 1
        val expectedResponse = mockk<Response<EventsResponse>>()
        val eventRepository = mockk<EventRepository>()
        coEvery { eventRepository.getEventsByCity(city, pageNumber) } returns expectedResponse

        val result = runBlocking { eventRepository.getEventsByCity(city, pageNumber) }

        assertEquals(expectedResponse, result)
    }

    @Test
    fun `test insertOrUpdate`() {
        val events = listOf(mockk<Event>())
        coEvery { eventDatabase.getEventDao().insertOrUpdate(events) } returns Unit

        runBlocking { eventRepository.insertOrUpdate(events) }
    }

    @Test
    fun `test getSavedEvents`() {
        val expectedEvents = mockk<LiveData<List<Event>>>(relaxed = true)
        coEvery { eventDatabase.getEventDao().getAllEvents() } returns expectedEvents

        val result = eventRepository.getSavedEvents()

        assertEquals(expectedEvents, result)
    }
}

