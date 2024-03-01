package com.mbonetti.tmchallenge.db.models

data class Venue(
    val id: String,
    val name: String,
    val address: Address,
    val city: City,
    val state: State,
    val url: String
)
