package com.mbonetti.tmchallenge

import com.mbonetti.tmchallenge.api.EventsResponse
import com.mbonetti.tmchallenge.db.models.Embedded
import com.mbonetti.tmchallenge.db.models.Event
import com.mbonetti.tmchallenge.db.models.EventImage
import com.mbonetti.tmchallenge.db.models.Page
import com.mbonetti.tmchallenge.repository.EventRepository
import com.mbonetti.tmchallenge.ui.EventViewModel
import com.mbonetti.tmchallenge.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class EventViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var eventRepository: EventRepository
    private lateinit var eventViewModel: EventViewModel

    @Before
    fun setup() {
        eventRepository = mockk()
        eventViewModel = EventViewModel(eventRepository)
    }

    @Test
    fun `test setSearchByCityChecked`() {
        eventViewModel.setSearchByCityChecked(true)
        assertEquals(true, eventViewModel.isSearchByCityChecked.value)
    }

    @Test
    fun `test getEvents`() = runTest(UnconfinedTestDispatcher()) {
        val eventImage = EventImage(
            url = "https://s1.ticketm.net/dam/a/c62/0636ff21-e369-4b8c-bee4-214ea0a81c62_1339761_CUSTOM.jpg",
            height = 305,
            width = 225,
            fallback = false,
        )
        val event = Event(
            id = "G5eVZ9R6mXJ34",
            name = "Charlotte Hornets vs. Phoenix Suns",
            url = "https://www.ticketmaster.com/phoenix-suns-vs-oklahoma-city-thunder-phoenix-arizona-03-03-2024/event/19005F0B52AA0E00",
            images = listOf(eventImage)
        )
        val embedded = Embedded(listOf(event))
        val page = Page(number = 0, size = 10, totalPages = 26000, totalElements = 259995)
        val eventsResponse = EventsResponse(embedded, page)
        val mockResponse = Response.success(eventsResponse)
        coEvery { eventRepository.getEvents(any()) } returns mockResponse

        eventViewModel.getEvents()

        val result = eventViewModel.events.value
        assert(result is Resource.Success)
        assertEquals(eventsResponse, (result as Resource.Success).data)
    }

    @Test
    fun `test searchEventsByKeywordOrCity`() = runTest(UnconfinedTestDispatcher()) {
        val eventImage = EventImage(
            url = "https://s1.ticketm.net/dam/a/c62/0636ff21-e369-4b8c-bee4-214ea0a81c62_1339761_CUSTOM.jpg",
            height = 305,
            width = 225,
            fallback = false,
        )
        val event = Event(
            id = "G5eVZ9R6mXJ34",
            name = "Charlotte Hornets vs. Phoenix Suns",
            url = "https://www.ticketmaster.com/phoenix-suns-vs-oklahoma-city-thunder-phoenix-arizona-03-03-2024/event/19005F0B52AA0E00",
            images = listOf(eventImage)
        )
        val embedded = Embedded(listOf(event))
        val page = Page(number = 0, size = 10, totalPages = 26000, totalElements = 259995)
        val eventsResponse = EventsResponse(embedded, page)
        val mockResponse = Response.success(eventsResponse)
        coEvery { eventRepository.getEventsByKeyword(any(), any()) } returns mockResponse

        eventViewModel.setSearchByCityChecked(false)
        eventViewModel.searchEventsByKeywordOrCity("Charlotte")

        val result = eventViewModel.events.value
        assert(result is Resource.Success)
        assertEquals(eventsResponse, (result as Resource.Success).data)
    }

}
