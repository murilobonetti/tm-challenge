package com.mbonetti.tmchallenge.models

import com.squareup.moshi.Json

data class Dates(
    @Json(name = "start")
    val startDate: StartDate,
)
