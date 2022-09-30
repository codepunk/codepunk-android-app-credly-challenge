package com.codepunk.credlychallenge.domain.model

import kotlinx.datetime.LocalDate

data class Person(
    val id: Int,
    val url: String?,
    val name: String,
    val birthday: LocalDate?,
    val deathDay: LocalDate?,
    val gender: String?,
    val images: Images?
)
