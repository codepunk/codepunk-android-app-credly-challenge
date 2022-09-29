package com.codepunk.credlychallenge.data.mapper

import com.codepunk.credlychallenge.data.local.model.ShowLocal
import com.codepunk.credlychallenge.data.remote.model.ShowRemote
import com.codepunk.credlychallenge.domain.model.Show

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
    images,
    summary
)

fun Show.toLocalModel(): ShowLocal = ShowLocal(
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
    images,
    summary
)