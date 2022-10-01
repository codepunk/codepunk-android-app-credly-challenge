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
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

/**
 * A Room entity describing an episode of a show.
 */
@Entity(
    tableName = "episode",
    foreignKeys = [
        ForeignKey(
            entity = ShowLocal::class,
            parentColumns = ["id"],
            childColumns = ["show_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("id"),
        Index("name")
    ]
)
data class EpisodeLocal(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "show_id")
    val showId: Int,

    val url: String,

    val name: String,

    val season: Int,

    val number: Int,

    @ColumnInfo(name = "airdate")
    val airDate: LocalDate,

    val runtime: Int,

    val images: ImagesLocal?,

    val summary: String?
)
