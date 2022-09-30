package com.codepunk.credlychallenge.data.remote.model

import com.codepunk.credlychallenge.domain.model.Images
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ShowRemote(
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

    @SerialName("image")
    val images: ImagesRemote?,

    val summary: String?
)