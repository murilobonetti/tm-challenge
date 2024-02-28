package com.mbonetti.tmchallenge.db.models

import com.squareup.moshi.Json

data class Dates(
    @Json(name = "start")
    val startDate: com.mbonetti.tmchallenge.db.models.StartDate,
)
