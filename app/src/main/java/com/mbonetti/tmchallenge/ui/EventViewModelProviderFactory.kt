package com.mbonetti.tmchallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mbonetti.tmchallenge.repository.EventRepository


class EventViewModelProviderFactory(
    val eventRepository: EventRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventViewModel(eventRepository) as T
    }
}
