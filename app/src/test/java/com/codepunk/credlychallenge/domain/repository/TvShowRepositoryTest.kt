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

import com.codepunk.credlychallenge.data.local.dao.*
import com.codepunk.credlychallenge.data.local.model.EpisodeLocal
import com.codepunk.credlychallenge.data.local.model.ShowLocal
import com.codepunk.credlychallenge.data.remote.api.TvShowApi
import com.codepunk.credlychallenge.data.remote.model.EpisodeRemote
import com.codepunk.credlychallenge.data.remote.model.ShowRemote
import com.codepunk.credlychallenge.data.repository.TvShowRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.toKotlinLocalDate
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(MockitoJUnitRunner::class)
class TvShowRepositoryTest {

    private val episodeLocalList = listOf(
        createEpisodeLocal(CACHED_SHOW_ID, 1),
        createEpisodeLocal(CACHED_SHOW_ID, 2),
        createEpisodeLocal(CACHED_SHOW_ID, 3)
    )

    private val episodeRemoteList = listOf(
        createEpisodeRemote(1),
        createEpisodeRemote(2),
        createEpisodeRemote(3)
    )

    private val showLocal: ShowLocal = mock {
        on { id } doReturn 1
        on { name } doReturn "Local"
    }

    private val showLocal2: ShowLocal = mock {
        on { id } doReturn 2
        on { name } doReturn "Local"
    }

    private val showRemote: ShowRemote = mock {
        on { id } doReturn 1
        on { name } doReturn "Remote"
    }

    private val showRemote2: ShowRemote = mock {
        on { id } doReturn 2
        on { name } doReturn "Remote"
    }

    private val tvShowApi: TvShowApi = mock {
        onBlocking { getShow(UNCACHED_SHOW_ID) } doReturn showRemote
        onBlocking { getShow(UNCACHED_SHOW_ID_2) } doReturn showRemote2
        onBlocking { getEpisodes(any()) } doReturn episodeRemoteList
    }

    private val showDao: ShowDao = mock {
        onBlocking { getShow(CACHED_SHOW_ID) } doReturn showLocal
        onBlocking { getShow(CACHED_SHOW_ID_2) } doReturn showLocal2
        onBlocking { getShow(UNCACHED_SHOW_ID) } doReturn null
        onBlocking { getShow(UNCACHED_SHOW_ID_2) } doReturn null
    }

    private val episodeDao: EpisodeDao = mock {
        onBlocking { getEpisodesForShow(CACHED_SHOW_ID) } doReturn episodeLocalList
        onBlocking { getEpisodesForShow(UNCACHED_SHOW_ID) } doReturn emptyList()
    }

    private val castEntryDao: CastEntryDao = mock()

    private val personDao: PersonDao = mock()

    private val characterDao: CharacterDao = mock()

    private val tvShowRepository = TvShowRepositoryImpl(
        tvShowApi,
        showDao,
        episodeDao,
        castEntryDao,
        personDao,
        characterDao
    )

    @Test
    fun `getShow should return a valid Show when show has already been cached`() =
        runTest(UnconfinedTestDispatcher()) {
            val flow = tvShowRepository.getShow(CACHED_SHOW_ID)
            val show = flow.first()
            assertNotNull(show)
        }

    @Test
    fun `getShow should return a valid Show when no shows have yet been cached`() =
        runTest(UnconfinedTestDispatcher()) {
            val flow = tvShowRepository.getShow(UNCACHED_SHOW_ID)
            val show = flow.first()
            assertNotNull(show)
        }

    @Test
    fun `getShow should skip remote API when results have been cached`() =
        runTest(UnconfinedTestDispatcher()) {
            val flow = tvShowRepository.getShow(CACHED_SHOW_ID)
            val show = flow.first()
            assertNotNull(show)
            assertEquals(show.name, "Local")
        }

    @Test
    fun `getShow should query remote API when results have not been cached`() =
        runTest(UnconfinedTestDispatcher()) {
            val flow = tvShowRepository.getShow(UNCACHED_SHOW_ID)
            val show = flow.first()
            assertNotNull(show)
            assertEquals(show.name, "Remote")
        }

    @Test
    fun `getEpisodes should return a valid list when episodes have already been cached`() =
        runTest(UnconfinedTestDispatcher()) {
            val flow = tvShowRepository.getEpisodes(CACHED_SHOW_ID)
            val episodes = flow.first()
            assertNotNull(episodes)
        }

    @Test
    fun `getEpisodes should return a valid list when now shows have yet been cached`() =
        runTest(UnconfinedTestDispatcher()) {
            val flow = tvShowRepository.getEpisodes(UNCACHED_SHOW_ID)
            val episodes = flow.first()
            assertNotNull(episodes)
        }

    @Test
    fun `getEpisodes should skip remote API when results have been cached`() =
        runTest(UnconfinedTestDispatcher()) {
            val flow = tvShowRepository.getEpisodes(CACHED_SHOW_ID)
            val episode = flow.first().first()
            assertEquals(episode.url, "Local")
        }

    @Test
    fun `getEpisodes should query remote API when results have not been cached`() =
        runTest(UnconfinedTestDispatcher()) {
            val flow = tvShowRepository.getEpisodes(UNCACHED_SHOW_ID)
            val episode = flow.first().first()
            assertEquals(episode.url, "Remote")
        }

    @Test
    fun `getShows should return a valid list when all shows have been cached`() =
        runTest(UnconfinedTestDispatcher()) {
            val flow = tvShowRepository.getShows(listOf(CACHED_SHOW_ID))
            val shows = flow.first()
            assertNotNull(shows)
        }

    @Test
    fun `getShows should return a valid list when no shows have been cached`() =
        runTest(UnconfinedTestDispatcher()) {
            val flow = tvShowRepository.getShows(listOf(UNCACHED_SHOW_ID))
            val shows = flow.first()
            assertNotNull(shows)
        }

    @Test
    fun `getShows should return a list of cached shows when all shows have been cached`() =
        runTest(UnconfinedTestDispatcher()) {
            val flow = tvShowRepository.getShows(listOf(CACHED_SHOW_ID, CACHED_SHOW_ID_2))
            val shows = flow.first()
            val size = shows.size
            val localCount = shows.count { it.name == "Local" }
            assert(size > 0)
            assertEquals(size, localCount)
        }

    @Test
    fun `getShows should return a list of shows from the API when no shows have been cached`() =
        runTest(UnconfinedTestDispatcher()) {
            val flow = tvShowRepository.getShows(listOf(UNCACHED_SHOW_ID, UNCACHED_SHOW_ID_2))
            val shows = flow.first()
            val size = shows.size
            val remoteCount = shows.count { it.name == "Remote" }
            assert(size > 0)
            assertEquals(size, remoteCount)
        }

    @Test
    fun `getShows should return a list of shows from both the local cache and the API when some shows have been cached`() =
        runTest(UnconfinedTestDispatcher()) {
            val flow = tvShowRepository.getShows(listOf(CACHED_SHOW_ID, UNCACHED_SHOW_ID))
            val shows = flow.first()
            val size = shows.size
            val localCount = shows.count { it.name == "Local" }
            val remoteCount = shows.count { it.name == "Remote" }
            assert(size > 0)
            assert(localCount > 0)
            assert(remoteCount > 0)
        }

    companion object {
        const val CACHED_SHOW_ID = 1
        const val UNCACHED_SHOW_ID = 2
        const val CACHED_SHOW_ID_2 = 3
        const val UNCACHED_SHOW_ID_2 = 4

        fun createEpisodeLocal(showId: Int, episodeId: Int): EpisodeLocal = EpisodeLocal(
            episodeId,
            showId,
            "Local",
            "Episode $episodeId",
            1,
            1,
            LocalDateTime.now().toLocalDate().toKotlinLocalDate(),
            30,
            null,
            null
        )

        fun createEpisodeRemote(episodeId: Int): EpisodeRemote = EpisodeRemote(
            episodeId,
            "Remote",
            "Episode $episodeId",
            1,
            1,
            LocalDateTime.now().toLocalDate().toKotlinLocalDate(),
            30,
            null,
            null
        )
    }
}