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

package com.codepunk.credlychallenge.util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

// Based on: https://proandroiddev.com/resilient-use-cases-with-kotlin-result-coroutines-and-annotations-511df10e2e16

/**
 * Like [runCatching], but with proper coroutines cancellation handling. Also only catches [Exception] instead of [Throwable].
 *
 * Cancellation exceptions need to be rethrown. See https://github.com/Kotlin/kotlinx.coroutines/issues/1814.
 */
inline fun <R> resultOf(block: () -> Flow<R>): Flow<Result<R>> =
    try {
        block()
            .map {
                runCatching { it }
            }
    } catch (e: TimeoutCancellationException) {
        flow { emit(Result.failure(e)) }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        flow { emit(Result.failure(e)) }
    }
