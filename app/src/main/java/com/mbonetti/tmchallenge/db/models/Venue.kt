package com.mbonetti.tmchallenge.db.models

data class Venue(
    val id: String,
    val name: String,
    val address: Address,
    val city: City,
    val state: State,
    val country: Country,
    val postalCode: String,
    val locale: String,
    val images: List<EventImage>,
    val markets: List<Market>,
    val url: String
)
