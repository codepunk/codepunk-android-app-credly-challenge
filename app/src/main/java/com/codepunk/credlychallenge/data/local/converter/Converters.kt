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

package com.codepunk.credlychallenge.data.local.converter

import androidx.room.TypeConverter
import com.codepunk.credlychallenge.data.local.model.ImagesLocal
import kotlinx.datetime.LocalDate
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * A set of [TypeConverter]s for converting data to and from a format that can be saved
 * in the local database.
 */
object Converters {

    // region Methods

    @TypeConverter
    fun toList(value: String?): List<String>? = value?.run { Json.decodeFromString(this) }

    @TypeConverter
    fun toString(list: List<String>?): String? = list?.run { Json.encodeToString(this) }

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.run {
        LocalDate.parse(value)
    }

    @TypeConverter
    fun toString(date: LocalDate?): String? = date?.toString()

    @TypeConverter
    fun toImages(value: String?): ImagesLocal? =
        value?.run {
            val imagesLocal = Json.decodeFromString<ImagesLocal>(this)
            imagesLocal
        }

    @TypeConverter
    fun toString(images: ImagesLocal?): String? =
        images?.run {
            val string = Json.encodeToString(this)
            string
        }

    // endregion Methods

}