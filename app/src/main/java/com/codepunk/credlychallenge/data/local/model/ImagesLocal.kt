package com.codepunk.credlychallenge.data.local.model

import kotlinx.serialization.Serializable

@Serializable
data class ImagesLocal(
    val medium: String?,
    val original: String?
)