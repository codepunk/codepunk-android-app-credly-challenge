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
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

/**
 * A Room entity describing a person (i.e. actor/actress).
 */
@Entity(
    tableName = "person",
    indices = [
        Index("id"),
        Index("name")
    ]
)
data class PersonLocal(
    @PrimaryKey
    val id: Int,

    val url: String?,

    val name: String,

    val birthday: LocalDate?,

    @ColumnInfo(name = "death_day")
    val deathDay: LocalDate?,

    val gender: String?,

    val images: ImagesLocal?
)
