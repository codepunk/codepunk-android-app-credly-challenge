package com.codepunk.credlychallenge.domain.model

import kotlinx.datetime.LocalDate

data class Episode(
    val id: Int,
    val url: String,
    val name: String,
    val season: Int,
    val number: Int,
    val airDate: LocalDate,
    val runtime: Int,
    val images: Images?,
    val summary: String?
)
