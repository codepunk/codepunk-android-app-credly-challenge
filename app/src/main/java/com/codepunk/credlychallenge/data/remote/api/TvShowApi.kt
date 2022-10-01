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

package com.codepunk.credlychallenge.data.remote.api

import com.codepunk.credlychallenge.data.remote.model.CastEntryRemote
import com.codepunk.credlychallenge.data.remote.model.EpisodeRemote
import com.codepunk.credlychallenge.data.remote.model.ShowRemote
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * The API for querying remote data.
 */
interface TvShowApi {

    // region Methods

    @GET("shows/{id}")
    suspend fun getShow(@Path("id") id: Int): ShowRemote?

    @GET("shows/{id}/episodes")
    suspend fun getEpisodes(@Path("id") id: Int): List<EpisodeRemote>

    @GET("shows/{id}/cast")
    suspend fun getCast(@Path("id") id: Int): List<CastEntryRemote>

    // endregion Methods

}
