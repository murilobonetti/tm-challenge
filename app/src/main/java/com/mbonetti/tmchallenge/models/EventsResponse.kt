package com.mbonetti.tmchallenge.models

import com.squareup.moshi.Json

data class EventsResponse(
    @Json(name = "_embedded")
    val embedded: Embedded,
    val page: Page
)
