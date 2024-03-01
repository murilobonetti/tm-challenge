package com.mbonetti.tmchallenge

import android.app.Application
import com.mbonetti.tmchallenge.repository.EventRepository
import com.mbonetti.tmchallenge.ui.EventViewModel
import com.mbonetti.tmchallenge.ui.EventViewModelProviderFactory
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class EventViewModelProviderFactoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun testCreate() {
        val application = mockk<Application>()
        val eventRepository = mockk<EventRepository>()

        val factory = EventViewModelProviderFactory(application, eventRepository)
        val viewModel = factory.create(EventViewModel::class.java)

        assertEquals(EventViewModel::class.java, viewModel.javaClass)
    }
}



