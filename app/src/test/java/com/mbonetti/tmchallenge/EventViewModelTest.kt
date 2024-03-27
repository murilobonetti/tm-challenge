package com.mbonetti.tmchallenge

import com.mbonetti.tmchallenge.api.EventsResponse
import com.mbonetti.tmchallenge.db.models.Embedded
import com.mbonetti.tmchallenge.db.models.Event
import com.mbonetti.tmchallenge.db.models.EventImage
import com.mbonetti.tmchallenge.db.models.Page
import com.mbonetti.tmchallenge.repository.EventRepositoryImpl
import com.mbonetti.tmchallenge.ui.EventViewModel
import com.mbonetti.tmchallenge.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response

@ExperimentalCoroutinesApi
class EventViewModelTest {

    private lateinit var eventRepositoryImpl: EventRepositoryImpl
    private lateinit var viewModel: EventViewModel
    private val application: EventsApplication = mockk()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        eventRepositoryImpl = mockk(relaxed = true)
        viewModel = EventViewModel(application, eventRepositoryImpl)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test setSearchByCityChecked`() {
        viewModel.setSearchByCityChecked(true)
        assertEquals(true, viewModel.isSearchByCityChecked.value)
    }

    @Test
    fun `test searchEventsByKeywordOrCity`() = runTest(UnconfinedTestDispatcher()) {
        val eventImage = EventImage(
            url = "https://s1.ticketm.net/dam/a/c62/0636ff21-e369-4b8c-bee4-214ea0a81c62_1339761_CUSTOM.jpg",
            ratio = "4_3",
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
        val embedded = Embedded(mutableListOf(event))
        val page = Page(number = 0, size = 10, totalPages = 26000, totalElements = 259995)
        val eventsResponse = EventsResponse(embedded, page)
        val mockResponse = Response.success(eventsResponse)
        coEvery { eventRepositoryImpl.getEventsByKeyword(any(), any()) } returns mockResponse

        viewModel.setSearchByCityChecked(false)
        viewModel.searchEventsByKeywordOrCity("Charlotte")

        val result = viewModel.events.value
        assert(result is Resource.Success)
        Assert.assertEquals(eventsResponse, (result as Resource.Success).data)
    }

    @Test
    fun `test safeGetEventsCall with Error result`() = runTest(UnconfinedTestDispatcher()) {
        val eventImage = EventImage(
            url = "https://s1.ticketm.net/dam/a/c62/0636ff21-e369-4b8c-bee4-214ea0a81c62_1339761_CUSTOM.jpg",
            ratio = "4_3",
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
        val embedded = Embedded(mutableListOf(event))
        val page = Page(number = 0, size = 10, totalPages = 26000, totalElements = 259995)
        val eventsResponse = EventsResponse(embedded, page)
        val mockResponse = Response.success(eventsResponse)
        coEvery { eventRepositoryImpl.getEvents(any()) } returns mockResponse

        viewModel.getEvents()

        val result = viewModel.events.value
        assert(result is Resource.Error)
    }

}
