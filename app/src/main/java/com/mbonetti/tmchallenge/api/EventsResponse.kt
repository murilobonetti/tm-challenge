package com.mbonetti.tmchallenge.api

import com.mbonetti.tmchallenge.db.models.Embedded
import com.mbonetti.tmchallenge.db.models.Page
import com.squareup.moshi.Json

data class EventsResponse(
    @Json(name = "_embedded")
    val embedded: Embedded?,
    val page: Page?,
)
