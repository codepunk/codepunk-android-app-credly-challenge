package com.codepunk.credlychallenge.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ImagesRemote(
    val medium: String?,
    val original: String?
)
