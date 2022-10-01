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

import com.codepunk.credlychallenge.domain.model.Show
import com.codepunk.credlychallenge.domain.repository.TvShowRepository
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
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.assertEquals
import kotlin.test.fail

@RunWith(MockitoJUnitRunner::class)
class GetShowsUseCaseTest {

    @Mock
    lateinit var show1: Show

    @Mock
    lateinit var show2: Show

    @Mock
    lateinit var show3: Show

    private val tvShowRepository: TvShowRepository = mock {
        on { getShows(DEFAULT_SHOW_IDS) } doReturn flow { emit(listOf(show1, show2, show3)) }
        on { getShows(EMPTY_SHOW_IDS) } doReturn flow { emit(emptyList()) }
        on { getShows(CANCELLING_SHOW_IDS) } doThrow CancellationException()
        on { getShows(OTHER_EXCEPTION_SHOW_IDS) } doThrow RuntimeException(TEST_EXCEPTION_MESSAGE)
    }

    private val getShowsUseCase: GetShowsUseCase = GetShowsUseCase { idList ->
        getShows(idList, tvShowRepository)
    }

    @Test
    fun `should return result wrapped in success if repository doesn't throw`() =
        runTest(UnconfinedTestDispatcher()) {
            val value = getShowsUseCase(DEFAULT_SHOW_IDS).first()
            assert(value.isSuccess)
        }

    @Test
    fun `should return result wrapped in success if repository returns empty list`() =
        runTest(UnconfinedTestDispatcher()) {
            val value = getShowsUseCase(EMPTY_SHOW_IDS).first()
            value.onSuccess {
                assert(it.isEmpty())
            }.onFailure {
                fail("getShowsUseCase returned Failure when Success was expected")
            }
        }

    @Test
    fun `should rethrow if repository throws CancellationException`() =
        runTest(UnconfinedTestDispatcher()) {
            val exception = try {
                getShowsUseCase(CANCELLING_SHOW_IDS)
                null
            } catch (e: CancellationException) {
                e
            }

            assert(exception is CancellationException)
        }

    @Test
    fun `should return result wrapped in failure if repository throws other exception`() =
        runTest(UnconfinedTestDispatcher()) {
            val value = getShowsUseCase(OTHER_EXCEPTION_SHOW_IDS).first()
            value.onSuccess {
                fail("getShowsUseCase returned Success when Failure was expected")
            }.onFailure {
                assertEquals(it.message, TEST_EXCEPTION_MESSAGE)
            }
        }

    companion object {
        val DEFAULT_SHOW_IDS = listOf(1, 2, 3)
        val EMPTY_SHOW_IDS = emptyList<Int>()
        val CANCELLING_SHOW_IDS = listOf(1)
        val OTHER_EXCEPTION_SHOW_IDS = listOf(2)

        const val TEST_EXCEPTION_MESSAGE = "Other exception"
    }

}
