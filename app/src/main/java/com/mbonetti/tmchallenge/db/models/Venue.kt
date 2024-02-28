package com.mbonetti.tmchallenge.db.models

data class Venue(
    val id: String,
    val name: String,
    val address: com.mbonetti.tmchallenge.db.models.Address,
    val city: com.mbonetti.tmchallenge.db.models.City,
    val state: com.mbonetti.tmchallenge.db.models.State,
    val country: com.mbonetti.tmchallenge.db.models.Country,
    val postalCode: String,
    val locale: String,
    val images: List<com.mbonetti.tmchallenge.db.models.EventImage>,
    val markets: List<com.mbonetti.tmchallenge.db.models.Market>,
    val url: String
)
