package com.codepunk.credlychallenge.data.repository

import com.codepunk.credlychallenge.data.local.dao.ShowDao
import com.codepunk.credlychallenge.data.mapper.toDomainModel
import com.codepunk.credlychallenge.data.mapper.toLocalModel
import com.codepunk.credlychallenge.data.remote.api.TvShowApi
import com.codepunk.credlychallenge.domain.model.SearchResult
import com.codepunk.credlychallenge.domain.model.Show
import com.codepunk.credlychallenge.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val tvShowApi: TvShowApi,
    private val showDao: ShowDao
) : TvShowRepository {

    override fun searchShows(query: String): Flow<List<SearchResult>> = flow {
        emit(
            tvShowApi.searchShows(query).map {
                it.toDomainModel()
            }
        )
    }

    override fun getShows(ids: List<Int>): Flow<List<Show>> = flow {
        val list = mutableListOf<Show>()
        ids.forEach { id ->
            showDao.getShow(id)?.toDomainModel() ?: run {
                // The show didn't exist in the local db, so fetch remote & save it
                tvShowApi.getShow(id)
                    ?.toDomainModel()
                    ?.also { show ->
                        showDao.saveShow(show.toLocalModel())
                    }
            }?.run {
                list.add(this)
            }
        }
        emit(list.toList())
    }

}