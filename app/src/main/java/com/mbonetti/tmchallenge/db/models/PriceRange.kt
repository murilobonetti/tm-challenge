package com.mbonetti.tmchallenge.db.models

data class PriceRange(
    val currency: String,
    val max: Double,
    val min: Double,
    val type: String
)
