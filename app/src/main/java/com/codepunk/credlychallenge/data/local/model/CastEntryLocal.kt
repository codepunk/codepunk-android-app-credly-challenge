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

package com.codepunk.credlychallenge.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * A Room entity describing a cast entry. A cast entry joins a person (i.e. actor/actress)
 * to a character they play in a show.
 */
@Entity(
    tableName = "cast",
    primaryKeys = ["show_id", "person_id", "character_id"],
    foreignKeys = [
        ForeignKey(
            entity = ShowLocal::class,
            parentColumns = ["id"],
            childColumns = ["show_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PersonLocal::class,
            parentColumns = ["id"],
            childColumns = ["person_id"]
        ),
        ForeignKey(
            entity = CharacterLocal::class,
            parentColumns = ["id"],
            childColumns = ["character_id"]
        )
    ]
)
data class CastEntryLocal(
    @ColumnInfo(name = "show_id")
    val showId: Int,

    @ColumnInfo(name = "person_id")
    val personId: Int,

    @ColumnInfo(name = "character_id")
    val characterId: Int,

    val self: Boolean?,

    val voice: Boolean?
)
