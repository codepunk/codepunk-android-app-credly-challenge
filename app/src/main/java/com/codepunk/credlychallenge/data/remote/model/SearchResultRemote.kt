package com.codepunk.credlychallenge.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchResultRemote(
    val score: Float,
    val show: ShowRemote
)
