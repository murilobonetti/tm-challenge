package com.mbonetti.tmchallenge.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_image")
data class EventImage(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "event_id")
    val eventId: String,
    val fallback: Boolean,
    val height: Int,
    val ratio: String,
    val url: String,
    val width: Int
)
