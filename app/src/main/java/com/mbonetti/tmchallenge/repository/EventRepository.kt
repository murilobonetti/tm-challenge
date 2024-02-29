package com.mbonetti.tmchallenge.repository

import com.mbonetti.tmchallenge.api.RetrofitInstance
import com.mbonetti.tmchallenge.db.EventDatabase

class EventRepository(
    val database: EventDatabase
) {

    suspend fun getEvents(pageNumber: Int) =
        RetrofitInstance.api.getEvents(pageNumber = pageNumber)
}
