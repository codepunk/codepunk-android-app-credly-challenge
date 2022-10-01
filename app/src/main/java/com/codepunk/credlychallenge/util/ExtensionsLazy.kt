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

/**
 * A thread-safe way of determining whether this [Lazy] instance was initialized, but also forcing
 * initialization to occur if not.
 */
@Suppress("UNUSED")
fun <T> Lazy<T>.wasInitialized(): Boolean =
    synchronized(this) { isInitialized().also { value } }

/**
 * Calls [onConsume] if this [Lazy] instance is uninitialized, and returns the encapsulated value.
 */
@Suppress("UNUSED")
fun <T> Lazy<T>.consume(onConsume: (T) -> Unit): T =
    if (wasInitialized()) value else value.also(onConsume)
