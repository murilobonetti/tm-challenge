package com.mbonetti.tmchallenge.api

import com.mbonetti.tmchallenge.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsApi {

    @GET("events.json")
    suspend fun getEvents(
        @Query("apikey") apiKey: String = BuildConfig.API_KEY,
        @Query("page") pageNumber: Int = 0,
        @Query("keyword") searchKeyword: String? = null,
        @Query("city") city: String? = null,
    ): Response<EventsResponse>


}
