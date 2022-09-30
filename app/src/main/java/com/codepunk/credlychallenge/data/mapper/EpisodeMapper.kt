package com.codepunk.credlychallenge.data.mapper

import com.codepunk.credlychallenge.data.local.model.EpisodeLocal
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

fun Episode.toLocalModel(showId: Int): EpisodeLocal = EpisodeLocal(
    id,
    showId,
    url,
    name,
    season,
    number,
    airDate,
    runtime,
    images?.toLocalModel(),
    summary
)

fun EpisodeLocal.toDomainModel(): Episode = Episode(
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