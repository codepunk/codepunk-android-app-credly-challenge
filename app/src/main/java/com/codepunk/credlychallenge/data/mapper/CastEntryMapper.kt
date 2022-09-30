package com.codepunk.credlychallenge.data.mapper

import com.codepunk.credlychallenge.data.local.model.CastEntryLocal
import com.codepunk.credlychallenge.data.remote.model.CastEntryRemote
import com.codepunk.credlychallenge.domain.model.CastEntry
import com.codepunk.credlychallenge.domain.model.Character
import com.codepunk.credlychallenge.domain.model.Person

fun CastEntryRemote.toDomainModel(): CastEntry = CastEntry(
    person.toDomainModel(),
    character.toDomainModel(),
    self,
    voice
)

fun CastEntry.toLocalModel(
    showId: Int
): CastEntryLocal = CastEntryLocal(
    showId,
    person.id,
    character.id,
    self,
    voice
)

fun CastEntryLocal.toDomainModel(
    person: Person,
    character: Character
): CastEntry = CastEntry(
    person,
    character,
    self,
    voice
)