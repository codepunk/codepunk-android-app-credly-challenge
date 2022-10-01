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

package com.codepunk.credlychallenge.data.remote.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A remote entity describing an episode of a show.
 */
@Serializable
data class EpisodeRemote(
    val id: Int,

    val url: String,

    val name: String,

    val season: Int,

    val number: Int,

    @SerialName("airdate")
    val airDate: LocalDate,

    val runtime: Int,

    @SerialName("image")
    val images: ImagesRemote?,

    val summary: String?
)
