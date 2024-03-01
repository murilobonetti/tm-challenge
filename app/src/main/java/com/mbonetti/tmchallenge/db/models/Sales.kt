package com.mbonetti.tmchallenge.db.models

data class Sales(
    val presales: List<com.mbonetti.tmchallenge.db.models.Presale>,
    val `public`: com.mbonetti.tmchallenge.db.models.Public
)
