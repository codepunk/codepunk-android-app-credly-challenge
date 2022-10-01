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

package com.codepunk.credlychallenge.domain.repository

import com.codepunk.credlychallenge.domain.model.CastEntry
import com.codepunk.credlychallenge.domain.model.Episode
import com.codepunk.credlychallenge.domain.model.Show
import kotlinx.coroutines.flow.Flow

/**
 * The interface for the TV show repository. Defining this repository as an interface allows us
 * to decouple the "how" (i.e. Room and Retrofit) of retrieving data from the domain, which
 * makes its requests via the methods below.
 */
interface TvShowRepository {

    // region Methods

    /**
     * Gets the details for a single [Show] with the given [showId].
     */
    fun getShow(showId: Int): Flow<Show?>

    /**
     * Gets details for all [Show]s corresponding to the given [showIds].
     */
    fun getShows(showIds: List<Int>): Flow<List<Show>>

    /**
     * Gets all [Episode]s for the given [showId].
     */
    fun getEpisodes(showId: Int): Flow<List<Episode>>

    /**
     * Gets the information about the cast for the given [showId].
     */
    fun getCast(showId: Int): Flow<List<CastEntry>>

    // endregion Methods

}
