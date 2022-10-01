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
import kotlin.test.assertNull
import kotlin.test.fail

@RunWith(MockitoJUnitRunner::class)
class GetShowUseCaseTest {

    @Mock
    lateinit var show: Show

    private val tvShowRepository: TvShowRepository = mock {
        on { getShow(DEFAULT_SHOW_ID) } doReturn flow { emit(show) }
        on { getShow(NOT_FOUND_SHOW_ID) } doReturn flow { emit(null) }
        on { getShow(CANCELLING_SHOW_ID) } doThrow CancellationException()
        on { getShow(OTHER_EXCEPTION_SHOW_ID) } doThrow RuntimeException(
            TEST_EXCEPTION_MESSAGE
        )
    }

    private val getShowUseCase: GetShowUseCase = GetShowUseCase { showId ->
        getShow(showId, tvShowRepository)
    }

    @Test
    fun `should return result wrapped in success if repository doesn't throw`() =
        runTest(UnconfinedTestDispatcher()) {
            val value = getShowUseCase(DEFAULT_SHOW_ID).first()
            assert(value.isSuccess)
        }

    @Test
    fun `should return null result wrapped in success if repository returns null`() =
        runTest(UnconfinedTestDispatcher()) {
            val value = getShowUseCase(NOT_FOUND_SHOW_ID).first()
            value.onSuccess {
                assertNull(it)
            }.onFailure {
                fail("getShowsUseCase returned Failure when Success was expected")
            }
        }

    @Test
    fun `should rethrow if repository throws CancellationException`() =
        runTest(UnconfinedTestDispatcher()) {
            val exception = try {
                getShowUseCase(CANCELLING_SHOW_ID)
                null
            } catch (e: CancellationException) {
                e
            }

            assert(exception is CancellationException)
        }

    @Test
    fun `should return result wrapped in failure if repository throws other exception`() =
        runTest(UnconfinedTestDispatcher()) {
            val value = getShowUseCase(OTHER_EXCEPTION_SHOW_ID).first()
            value.onSuccess {
                fail("getShowsUseCase returned Success when Failure was expected")
            }.onFailure {
                assertEquals(it.message, TEST_EXCEPTION_MESSAGE)
            }
        }

    companion object {
        const val DEFAULT_SHOW_ID = 1
        const val NOT_FOUND_SHOW_ID = 2
        const val CANCELLING_SHOW_ID = 3
        const val OTHER_EXCEPTION_SHOW_ID = 4

        const val TEST_EXCEPTION_MESSAGE = "Other exception"
    }
    
}
