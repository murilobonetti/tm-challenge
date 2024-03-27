package com.mbonetti.tmchallenge.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mbonetti.tmchallenge.repository.EventRepository
import com.mbonetti.tmchallenge.repository.EventRepositoryImpl
import javax.inject.Inject


class EventViewModelProviderFactory @Inject constructor(
    val application: Application,
    val eventRepositoryImpl: EventRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventViewModel(application, eventRepositoryImpl) as T
    }
}
