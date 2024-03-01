package com.mbonetti.tmchallenge.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mbonetti.tmchallenge.repository.EventRepository


class EventViewModelProviderFactory(
    val application: Application,
    val eventRepository: EventRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventViewModel(application, eventRepository) as T
    }
}
