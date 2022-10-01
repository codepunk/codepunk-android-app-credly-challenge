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
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * A remote entity describing a show.
 */
@Serializable
data class ShowRemote(
    val id: Int,

    val name: String,

    val url: String?,

    val type: String?,

    val language: String?,

    val genres: List<String>?,

    val status: String?,

    val runtime: Int?,

    val averageRuntime: Int?,

    val premiered: LocalDate?,

    val ended: LocalDate?,

    val officialSite: String?,

    @SerialName("image")
    val images: ImagesRemote?,

    val summary: String?
)
