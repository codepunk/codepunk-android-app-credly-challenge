package com.codepunk.credlychallenge.data.remote.api

import com.codepunk.credlychallenge.data.remote.model.CastEntryRemote
import com.codepunk.credlychallenge.data.remote.model.EpisodeRemote
import com.codepunk.credlychallenge.data.remote.model.ShowRemote
import retrofit2.http.GET
import retrofit2.http.Path

interface TvShowApi {

    @GET("shows/{id}")
    suspend fun getShow(@Path("id") id: Int): ShowRemote?

    @GET("shows/{id}/episodes")
    suspend fun getEpisodes(@Path("id") id: Int): List<EpisodeRemote>

    @GET("shows/{id}/cast")
    suspend fun getCast(@Path("id") id: Int): List<CastEntryRemote>

}