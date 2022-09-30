package com.codepunk.credlychallenge.domain.model

data class CastEntry(
    val person: Person,
    val character: Character,
    val self: Boolean?,
    val voice: Boolean?
)
