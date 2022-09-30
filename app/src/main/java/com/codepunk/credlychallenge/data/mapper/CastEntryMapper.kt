package com.codepunk.credlychallenge.data.mapper

import com.codepunk.credlychallenge.data.remote.model.CastEntryRemote
import com.codepunk.credlychallenge.domain.model.CastEntry

fun CastEntryRemote.toDomainModel(): CastEntry = CastEntry(
    person.toDomainModel(),
    character.toDomainModel(),
    self,
    voice
)