package com.mbonetti.tmchallenge.db.models

data class EmbeddedVenue(
    val venues: List<com.mbonetti.tmchallenge.db.models.Venue>
)
