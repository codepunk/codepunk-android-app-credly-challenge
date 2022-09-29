package com.codepunk.credlychallenge.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExternalsRemote(

    @SerialName("tvrage")
    val tvRage: Int?,

    @SerialName("thetvdb")
    val theTvDb: Int?,

    val imdb: String?

)
