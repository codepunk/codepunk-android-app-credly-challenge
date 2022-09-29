package com.codepunk.credlychallenge.data.mapper

import com.codepunk.credlychallenge.data.local.model.ShowLocal
import com.codepunk.credlychallenge.data.remote.model.ExternalsRemote
import com.codepunk.credlychallenge.data.remote.model.ShowRemote
import com.codepunk.credlychallenge.domain.model.Externals
import com.codepunk.credlychallenge.domain.model.Show

fun ExternalsRemote.toDomainModel(): Externals = Externals(tvRage, theTvDb, imdb)

fun ShowRemote.toDomainModel(): Show = Show(
    id,
    name,
    url,
    type,
    language,
    genres,
    status,
    runtime,
    averageRuntime,
    premiered,
    ended,
    officialSite,
    externals?.toDomainModel(),
    images,
    summary
)

fun Show.toLocalModel(): ShowLocal = ShowLocal(
    id,
    name,
    externals?.imdb ?: "",
    url,
    type,
    language,
    genres,
    status,
    runtime,
    averageRuntime,
    premiered,
    ended,
    officialSite,
    externals,
    images,
    summary
)

fun ShowLocal.toDomainModel(): Show = Show(
    id,
    name,
    url,
    type,
    language,
    genres,
    status,
    runtime,
    averageRuntime,
    premiered,
    ended,
    officialSite,
    externals,
    images,
    summary
)