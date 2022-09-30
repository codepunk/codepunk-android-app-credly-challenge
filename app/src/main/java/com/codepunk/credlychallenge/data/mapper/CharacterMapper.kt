package com.codepunk.credlychallenge.data.mapper

import com.codepunk.credlychallenge.data.remote.model.CharacterRemote
import com.codepunk.credlychallenge.domain.model.Character

fun CharacterRemote.toDomainModel(): Character = Character(
    id,
    url,
    name,
    images?.toDomainModel()
)