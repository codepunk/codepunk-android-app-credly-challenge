package com.codepunk.credlychallenge.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterRemote(
    val id: Int,

    val url: String?,

    val name: String,

    @SerialName("image")
    val images: ImagesRemote?
)
