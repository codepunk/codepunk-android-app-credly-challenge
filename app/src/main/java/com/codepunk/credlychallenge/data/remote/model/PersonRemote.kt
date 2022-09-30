package com.codepunk.credlychallenge.data.remote.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonRemote(
    val id: Int,

    val url: String?,

    val name: String,

    val birthday: LocalDate?,

    @SerialName("deathday")
    val deathDay: LocalDate?,

    val gender: String?,

    @SerialName("image")
    val images: ImagesRemote?
)
