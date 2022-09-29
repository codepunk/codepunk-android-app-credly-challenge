package com.codepunk.credlychallenge.data.remote.api

import com.codepunk.credlychallenge.data.remote.model.SearchResultRemote
import com.codepunk.credlychallenge.data.remote.model.ShowRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowApi {

    @GET("search/shows")
    suspend fun searchShows(@Query("q") query: String): List<SearchResultRemote>

    @GET("lookup/shows")
    suspend fun getShow(@Query("imdb") imdb: String): ShowRemote?

}