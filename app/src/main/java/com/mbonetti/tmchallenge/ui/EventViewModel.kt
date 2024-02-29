package com.mbonetti.tmchallenge.ui

import androidx.lifecycle.LiveData
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

    private val _isSearchByCityChecked = MutableLiveData<Boolean>()
    val isSearchByCityChecked: LiveData<Boolean> get() = _isSearchByCityChecked

    init {
        getEvents()
    }

    fun setSearchByCityChecked(isChecked: Boolean) {
        _isSearchByCityChecked.value = isChecked
    }

    fun getEvents() = viewModelScope.launch {
        events.postValue(Resource.Loading())
        val response = eventRepository.getEvents(pageNumber = eventsPage)
        events.postValue(handleEventsResponse(response))
    }

    fun searchEventsByKeywordOrCity(searchQuery: String) = viewModelScope.launch {
        events.postValue(Resource.Loading())
        val response = if (isSearchByCityChecked.value == true) {
            eventRepository.getEventsByCity(
                city = searchQuery,
                pageNumber = eventsPage
            )
        } else {
            eventRepository.getEventsByKeyword(
                keyword = searchQuery,
                pageNumber = eventsPage
            )
        }

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
