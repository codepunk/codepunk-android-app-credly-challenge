package com.codepunk.credlychallenge.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Externals(
    val tvRage: Int?,
    val theTvDb: Int?,
    val imdb: String?
)
