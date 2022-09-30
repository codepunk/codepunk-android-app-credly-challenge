package com.codepunk.credlychallenge.data.mapper

import com.codepunk.credlychallenge.data.remote.model.EpisodeRemote
import com.codepunk.credlychallenge.domain.model.Episode

fun EpisodeRemote.toDomainModel(): Episode = Episode(
    id,
    url,
    name,
    season,
    number,
    airDate,
    runtime,
    images?.toDomainModel(),
    summary
)