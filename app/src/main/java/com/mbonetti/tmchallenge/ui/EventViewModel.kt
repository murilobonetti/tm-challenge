package com.mbonetti.tmchallenge.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mbonetti.tmchallenge.EventsApplication
import com.mbonetti.tmchallenge.api.EventsResponse
import com.mbonetti.tmchallenge.db.models.Event
import com.mbonetti.tmchallenge.repository.EventRepository
import com.mbonetti.tmchallenge.util.Resource
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response

class EventViewModel(
    application: Application,
    private val eventRepository: EventRepository
) : AndroidViewModel(application) {

    val events: MutableLiveData<Resource<EventsResponse>> = MutableLiveData()
    var eventsPage = 0
    var searchPage = 0

    private var eventsResponse: EventsResponse? = null
    private var searchResponse: EventsResponse? = null

    private val _isSearchByCityChecked = MutableLiveData<Boolean>()
    val isSearchByCityChecked: LiveData<Boolean> get() = _isSearchByCityChecked

    init {
        getEvents()
    }

    fun setSearchByCityChecked(isChecked: Boolean) {
        _isSearchByCityChecked.value = isChecked
    }

    fun getEvents() = viewModelScope.launch {
        safeGetEventsCall()
    }

    fun searchEventsByKeywordOrCity(searchQuery: String) = viewModelScope.launch {
        events.postValue(Resource.Loading())
        val response = if (isSearchByCityChecked.value == true) {
            eventRepository.getEventsByCity(
                city = searchQuery,
                pageNumber = searchPage
            )
        } else {
            eventRepository.getEventsByKeyword(
                keyword = searchQuery,
                pageNumber = searchPage
            )
        }

        events.postValue(handleSearchResponse(response))
    }

    private fun handleEventsResponse(response: Response<EventsResponse>): Resource<EventsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                eventsPage++
                if (eventsResponse == null) {
                    eventsResponse = resultResponse
                } else {
                    val oldEvents = eventsResponse?.embedded?.events
                    val newEvents = resultResponse.embedded?.events
                    if (newEvents != null) {
                        oldEvents?.addAll(newEvents)
                    }
                }
                resultResponse.embedded?.events?.let { saveEvents(it) }
                return Resource.Success(eventsResponse ?: resultResponse)
            }
        }

        return Resource.Error(response.message())
    }

    private fun handleSearchResponse(response: Response<EventsResponse>): Resource<EventsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { eventResponse ->
                searchPage++
                if (searchResponse == null) {
                    searchResponse = eventResponse
                } else {
                    val oldEvents = searchResponse?.embedded?.events
                    val newEvents = eventResponse.embedded?.events
                    if (newEvents != null) {
                        oldEvents?.addAll(newEvents)
                    }
                }
                eventResponse.embedded?.events?.let { saveEvents(it) }
                return Resource.Success(searchResponse ?: eventResponse)
            }
        }

        return Resource.Error(response.message())
    }

    private fun saveEvents(events: List<Event>) = viewModelScope.launch {
        eventRepository.insertOrUpdate(events)
    }

    fun getSavedEvents() = eventRepository.getSavedEvents()

    private suspend fun safeGetEventsCall() {
        events.postValue(Resource.Loading())
        try {
            if (hasNetworkConnection()) {
                val response = eventRepository.getEvents(pageNumber = eventsPage)
                events.postValue(handleEventsResponse(response))
            } else {
                events.postValue(Resource.Error("No internet connection!"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> events.postValue(Resource.Error("Network Failure"))
                else -> events.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasNetworkConnection(): Boolean {
        val connectivityManager = getApplication<EventsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities((activeNetwork)) ?: return false

        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
