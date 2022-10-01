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

import android.icu.text.DateTimePatternGenerator
import android.widget.TextView
import androidx.databinding.BindingAdapter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.DateTimeException
import java.time.format.DateTimeFormatter

// region Constants

private const val DATE_SKELETON = "MMMM d, yyyy"

private const val GENRE_SEPARATOR = " | "

// endregion Constants

// region Properties

private val DATE_FORMATTER = DateTimeFormatter.ofPattern(
    DateTimePatternGenerator.getInstance()
        .getBestPattern(
            DATE_SKELETON,
            DateTimePatternGenerator.MATCH_ALL_FIELDS_LENGTH
        )
)

// endregion Properties

// region Methods

/**
 * Binds a [LocalDate] to a [TextView].
 */
@BindingAdapter("bindLocalDate")
fun TextView.bindLocalDate(date: LocalDate?) {
    text = try {
        date?.toJavaLocalDate()?.format(DATE_FORMATTER) ?: ""
    } catch (e: DateTimeException) {
        "?"
    }
}

/**
 * Binds a list of genres to a [TextView].
 */
@BindingAdapter("bindGenres")
fun TextView.bindGenres(genres: List<String>) {
    text = genres.joinToString(GENRE_SEPARATOR)
}

// endregion Methods
