package com.codepunk.credlychallenge.data.mapper

import com.codepunk.credlychallenge.data.local.model.CharacterLocal
import com.codepunk.credlychallenge.data.remote.model.CharacterRemote
import com.codepunk.credlychallenge.domain.model.Character

fun CharacterRemote.toDomainModel(): Character = Character(
    id,
    url,
    name,
    images?.toDomainModel()
)

fun Character.toLocalModel(): CharacterLocal = CharacterLocal(
    id,
    url,
    name,
    images?.toLocalModel()
)

fun CharacterLocal.toDomainModel(): Character = Character(
    id,
    url,
    name,
    images?.toDomainModel()
)