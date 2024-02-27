package com.mbonetti.tmchallenge.models

import com.squareup.moshi.Json

data class Event(
    val id: String,
    @Json(name = "_embedded")
    val name: String,
    val pleaseNote: String,
    val dates: Dates,
    val url: String,
    val sales: Sales,
    val promoter: Promoter,
    val embeddedVenue: EmbeddedVenue,
    val accessibility: Accessibility,
    val seatmap: Seatmap,
    val images: List<EventImage>,
    val priceRanges: List<PriceRange>,
)
