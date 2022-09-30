package com.codepunk.credlychallenge.data.remote.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeRemote(
    val id: Int,

    val url: String,

    val name: String,

    val season: Int,

    val number: Int,

    @SerialName("airdate")
    val airDate: LocalDate,

    val runtime: Int,

    @SerialName("image")
    val images: ImagesRemote?,

    val summary: String?
)
