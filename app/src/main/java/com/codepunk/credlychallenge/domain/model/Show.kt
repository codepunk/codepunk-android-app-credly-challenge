package com.codepunk.credlychallenge.domain.model

import kotlinx.datetime.LocalDate

data class Show(
    val id: Int,
    val name: String,
    val url: String?,
    val type: String?,
    val language: String?,
    val genres: List<String>?,
    val status: String?,
    val runtime: Int?,
    val averageRuntime: Int?,
    val premiered: LocalDate?,
    val ended: LocalDate?,
    val officialSite: String?,
    val images: Images?,
    val summary: String?
)
