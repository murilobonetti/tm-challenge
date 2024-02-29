package com.mbonetti.tmchallenge.db.models

data class EventImage(
    val url: String,
    val ratio: String?,
    val fallback: Boolean,
    val height: Int,
    val width: Int,
)
