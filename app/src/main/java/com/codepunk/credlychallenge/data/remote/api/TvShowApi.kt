package com.codepunk.credlychallenge.data.remote.api

import com.codepunk.credlychallenge.data.remote.model.SearchResultRemote
import com.codepunk.credlychallenge.data.remote.model.ShowRemote
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApi {

    @GET("shows/{id}")
    suspend fun getShow(@Path("id") id: Int): ShowRemote?

    @GET("search/shows")
    suspend fun searchShows(@Query("q") query: String): List<SearchResultRemote>

}