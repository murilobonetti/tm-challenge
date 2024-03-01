package com.mbonetti.tmchallenge.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class Event(
    @PrimaryKey
    val id: String,
    val name: String,
    val url: String?,
//    @Json(name = "_embedded")
//    val embeddedVenue: EmbeddedVenue,
    val images: List<EventImage>,
//    val dates: Dates,
)
