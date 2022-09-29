package com.codepunk.credlychallenge.data.local.converters

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Converters {

    @TypeConverter
    fun toList(value: String?): List<String>? = value?.run { Json.decodeFromString(this) }

    @TypeConverter
    fun toString(list: List<String>?): String? = list?.run { Json.encodeToString(this) }

    @TypeConverter
    fun toMap(value: String?): Map<String, String>? =
        value?.run { Json.decodeFromString(this) }

    @TypeConverter
    fun toString(map: Map<String, String>?): String? = map?.run { Json.encodeToString(this) }

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.run {
        LocalDate.parse(value)
    }

    @TypeConverter
    fun toString(date: LocalDate?): String? = date?.toString()

}