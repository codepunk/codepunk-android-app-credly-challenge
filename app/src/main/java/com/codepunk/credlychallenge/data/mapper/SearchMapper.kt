package com.codepunk.credlychallenge.data.mapper

import com.codepunk.credlychallenge.data.remote.model.SearchResultRemote
import com.codepunk.credlychallenge.domain.model.SearchResult

fun SearchResultRemote.toDomainModel(): SearchResult =
    SearchResult(score, show.toDomainModel())