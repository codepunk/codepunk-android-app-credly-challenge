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

package com.codepunk.credlychallenge.domain.usecase

import com.codepunk.credlychallenge.domain.model.Episode
import com.codepunk.credlychallenge.domain.repository.TvShowRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import kotlin.test.assertEquals
import kotlin.test.fail

@RunWith(MockitoJUnitRunner::class)
class GetEpisodesUseCaseTest {

    @Mock
    lateinit var episode1: Episode

    @Mock
    lateinit var episode2: Episode

    @Mock
    lateinit var episode3: Episode

    private val tvShowRepository: TvShowRepository = mock {
        on { getEpisodes(DEFAULT_SHOW_ID) } doReturn flow {
            emit(listOf(episode1, episode2, episode3))
        }
        on { getEpisodes(NO_EPISODES_SHOW_ID) } doReturn flow { emit(emptyList()) }
        on { getEpisodes(CANCELLING_SHOW_ID) } doThrow CancellationException()
        on { getEpisodes(OTHER_EXCEPTION_SHOW_ID) } doThrow RuntimeException(
            TEST_EXCEPTION_MESSAGE
        )
    }

    private val getEpisodesUseCase: GetEpisodesUseCase = GetEpisodesUseCase { showId ->
        getEpisodes(showId, tvShowRepository)
    }

    @Test
    fun `should return result wrapped in success if repository doesn't throw`() =
        runTest(UnconfinedTestDispatcher()) {
            val value = getEpisodesUseCase(DEFAULT_SHOW_ID).first()
            assert(value.isSuccess)
        }

    @Test
    fun `should return result wrapped in success if repository returns empty list`() =
        runTest(UnconfinedTestDispatcher()) {
            val value = getEpisodesUseCase(NO_EPISODES_SHOW_ID).first()
            value.onSuccess {
                assert(it.isEmpty())
            }.onFailure {
                fail("getEpisodesUseCase returned Failure when Success was expected")
            }
        }

    @Test
    fun `should rethrow if repository throws CancellationException`() =
        runTest(UnconfinedTestDispatcher()) {
            val exception = try {
                getEpisodesUseCase(CANCELLING_SHOW_ID)
                null
            } catch (e: CancellationException) {
                e
            }

            assert(exception is CancellationException)
        }

    @Test
    fun `should return result wrapped in failure if repository throws other exception`() =
        runTest(UnconfinedTestDispatcher()) {
            val value = getEpisodesUseCase(OTHER_EXCEPTION_SHOW_ID).first()
            value.onSuccess {
                fail("getEpisodesUseCase returned Success when Failure was expected")
            }.onFailure {
                assertEquals(it.message, TEST_EXCEPTION_MESSAGE)
            }
        }

    companion object {
        const val DEFAULT_SHOW_ID = 1
        const val NO_EPISODES_SHOW_ID = 2
        const val CANCELLING_SHOW_ID = 3
        const val OTHER_EXCEPTION_SHOW_ID = 4

        const val TEST_EXCEPTION_MESSAGE = "Other exception"
    }

}
