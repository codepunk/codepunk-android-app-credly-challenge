package com.codepunk.credlychallenge.data.repository

import com.codepunk.credlychallenge.data.local.dao.ShowDao
import com.codepunk.credlychallenge.data.mapper.toDomainModel
import com.codepunk.credlychallenge.data.mapper.toLocalModel
import com.codepunk.credlychallenge.data.remote.api.TvShowApi
import com.codepunk.credlychallenge.domain.model.CastEntry
import com.codepunk.credlychallenge.domain.model.Episode
import com.codepunk.credlychallenge.domain.model.Show
import com.codepunk.credlychallenge.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val tvShowApi: TvShowApi,
    private val showDao: ShowDao
) : TvShowRepository {

    override fun getShow(id: Int): Flow<Show?> = flow {
        val show = showDao.getShow(id)?.toDomainModel() ?: run {
            // The show is not in the local db, so fetch remote & save it
            tvShowApi.getShow(id)
                ?.toDomainModel()
                ?.also { showDao.saveShow(it.toLocalModel()) }
        }

        emit(show)
    }

    override fun getShows(ids: List<Int>): Flow<List<Show>> = flow {
        val list = mutableListOf<Show>()
        ids.forEach { id ->
            val show = showDao.getShow(id)?.toDomainModel() ?: run {
                // The show is not in the local db, so fetch remote & save it
                tvShowApi.getShow(id)
                    ?.toDomainModel()
                    ?.also { showDao.saveShow(it.toLocalModel()) }
            }

            if (show != null) {
                list.add(show)
            }
        }
        emit(list.toList())
    }

    override fun getEpisodes(id: Int): Flow<List<Episode>> = flow {
        emit(
            tvShowApi.getEpisodes(id).map {
                it.toDomainModel()
            }
        )
    }

    override fun getCast(id: Int): Flow<List<CastEntry>> = flow {
        emit(
            tvShowApi.getCast(id).map {
                it.toDomainModel()
            }
        )
    }
}