/*
 * Copyright (C) 2022 Scott Slater
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.codepunk.credlychallenge.data.mapper

import com.codepunk.credlychallenge.data.local.model.EpisodeLocal
import com.codepunk.credlychallenge.data.remote.model.EpisodeRemote
import com.codepunk.credlychallenge.domain.model.Episode

/**
 * A set of methods used to map between remote, local, and domain episodes.
 */

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
