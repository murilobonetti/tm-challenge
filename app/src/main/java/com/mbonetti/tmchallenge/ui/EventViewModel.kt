package com.mbonetti.tmchallenge.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbonetti.tmchallenge.api.EventsResponse
import com.mbonetti.tmchallenge.repository.EventRepository
import com.mbonetti.tmchallenge.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class EventViewModel(
    val eventRepository: EventRepository
) : ViewModel() {

    val events: MutableLiveData<Resource<EventsResponse>> = MutableLiveData()
    var eventsPage = 0

    init {
        getEvents()
    }

    fun getEvents() = viewModelScope.launch {
        events.postValue(Resource.Loading())
        val response = eventRepository.getEvents(eventsPage)
        events.postValue(handleEventsResponse(response))

    }

    private fun handleEventsResponse(response: Response<EventsResponse>): Resource<EventsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { eventResponse ->
                return Resource.Success(eventResponse)
            }
        }

        return Resource.Error(response.message())
    }
}
