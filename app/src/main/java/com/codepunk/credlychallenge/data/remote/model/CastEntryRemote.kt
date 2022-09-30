package com.codepunk.credlychallenge.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CastEntryRemote(

    val person: PersonRemote,

    val character: CharacterRemote,

    val self: Boolean?,

    val voice: Boolean?

)
