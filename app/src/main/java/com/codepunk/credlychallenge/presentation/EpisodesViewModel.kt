package com.codepunk.credlychallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codepunk.credlychallenge.domain.model.Episode
import com.codepunk.credlychallenge.domain.usecase.GetEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    val getEpisodesUseCase: GetEpisodesUseCase
) : ViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _episodesResult =
        MutableStateFlow<Result<List<Episode>>>(Result.success(emptyList()))
    val episodesResult = _episodesResult.asStateFlow()

    private val _showError = MutableStateFlow<Lazy<Throwable>?>(null)
    val showError = _showError.asStateFlow()

    fun getEpisodes(showId: Int) {
        viewModelScope.launch {
            _loading.value = true
            getEpisodesUseCase(showId)
                .collect { result ->
                    _episodesResult.value = result
                    _loading.value = false
                }
        }
    }

}