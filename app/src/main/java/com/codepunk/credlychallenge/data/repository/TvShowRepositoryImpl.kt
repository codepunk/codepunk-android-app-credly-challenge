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

package com.codepunk.credlychallenge.data.repository

import com.codepunk.credlychallenge.data.local.dao.*
import com.codepunk.credlychallenge.data.mapper.toDomainModel
import com.codepunk.credlychallenge.data.mapper.toLocalModel
import com.codepunk.credlychallenge.data.remote.api.TvShowApi
import com.codepunk.credlychallenge.domain.model.CastEntry
import com.codepunk.credlychallenge.domain.model.Episode
import com.codepunk.credlychallenge.domain.model.Show
import com.codepunk.credlychallenge.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation of [TvShowRepository], used to get data related to shows, episodes, and cast.
 * In general, the local database is queried first. If that data exists, then it is used.
 * Otherwise, a remote call is made and the data is saved to the local database before
 * continuing.
 */
class TvShowRepositoryImpl @Inject constructor(
    private val tvShowApi: TvShowApi,
    private val showDao: ShowDao,
    private val episodeDao: EpisodeDao,
    private val castEntryDao: CastEntryDao,
    private val personDao: PersonDao,
    private val characterDao: CharacterDao
) : TvShowRepository {

    // region Methods

    /**
     * Gets the details for a single [Show] with the given [showId].
     */
    override fun getShow(showId: Int): Flow<Show?> = flow {
        val show = when (val cached = showDao.getShow(showId)?.toDomainModel()) {
            null -> tvShowApi.getShow(showId)?.toDomainModel()?.also {
                showDao.insert(it.toLocalModel())
            }
            else -> cached
        }
        emit(show)
    }

    /**
     * Gets details for all [Show]s corresponding to the given [showIds].
     */
    override fun getShows(showIds: List<Int>): Flow<List<Show>> = flow {
        val list = mutableListOf<Show>()
        showIds.forEach { id ->
            val show = showDao.getShow(id)?.toDomainModel() ?: run {
                // The show is not in the local db, so fetch remote & save it
                tvShowApi.getShow(id)
                    ?.toDomainModel()
                    ?.also { showDao.insert(it.toLocalModel()) }
            }

            if (show != null) {
                list.add(show)
            }
        }
        emit(list.toList())
    }

    /**
     * Gets all [Episode]s for the given [showId].
     */
    override fun getEpisodes(showId: Int): Flow<List<Episode>> = flow {
        val cached = episodeDao.getEpisodesForShow(showId).map { it.toDomainModel() }
        val episodes = when {
            cached.isEmpty() -> {
                tvShowApi.getEpisodes(showId).map { it.toDomainModel() }.also { fromRemote ->
                    episodeDao.insert(fromRemote.map { it.toLocalModel(showId) })
                }
            }
            else -> cached
        }

        emit(episodes)
    }

    /**
     * Gets the information about the cast for the given [showId].
     */
    override fun getCast(showId: Int): Flow<List<CastEntry>> = flow {
        val cached = castEntryDao.getCastForShow(showId)
        val cast = when {
            cached.isEmpty() -> {
                tvShowApi.getCast(showId).map { it.toDomainModel() }.also { fromRemote ->
                    val people = fromRemote.map { it.person.toLocalModel() }
                    personDao.insert(people)
                    val characters = fromRemote.map { it.character.toLocalModel() }
                    characterDao.insert(characters)
                    val cast = fromRemote.map { it.toLocalModel(showId) }
                    castEntryDao.insert(cast)
                }
            }
            else -> {
                // Build a CastEntry list from data stored in the local database
                val cachedPeople = personDao.getPeopleInShow(showId).associate {
                    it.id to it.toDomainModel()
                }
                val cachedCharacters = characterDao.getCharactersInShow(showId).associate {
                    it.id to it.toDomainModel()
                }
                cached.map {
                    it.toDomainModel(
                        cachedPeople.getOrElse(it.personId) {
                            throw IllegalStateException("Unable to get cast.")
                        },
                        cachedCharacters.getOrElse(it.characterId) {
                            throw IllegalStateException("Unable to get cast.")
                        }
                    )
                }
            }
        }
        emit(cast)
    }

    // endregion Methods

}
