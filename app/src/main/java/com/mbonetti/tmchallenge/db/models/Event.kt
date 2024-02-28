package com.mbonetti.tmchallenge.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "event")
data class Event(
    @PrimaryKey
    val id: String,
    val name: String,
    val pleaseNote: String,
    val url: String,
    val promoter: Promoter,
    @Json(name = "_embedded")
    val embeddedVenue: EmbeddedVenue,
    val images: List<EventImage>,
//    val priceRanges: List<PriceRange>,
//    val dates: Dates,
//    val seatmap: Seatmap,
//    val accessibility: Accessibility,
)
