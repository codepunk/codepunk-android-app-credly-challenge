package com.codepunk.credlychallenge.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codepunk.credlychallenge.domain.model.SearchResult
import com.codepunk.credlychallenge.domain.model.Show
import com.codepunk.credlychallenge.domain.usecase.GetShowsUseCase
import com.codepunk.credlychallenge.domain.usecase.SearchShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getShowsUseCase: GetShowsUseCase,
    private val searchShowsUseCase: SearchShowsUseCase
) : ViewModel() {
    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    /*
    private val _searchResults: MutableStateFlow<List<SearchResult>?> = MutableStateFlow(null)
    val searchResults = _searchResults.asStateFlow()

    private val _searchError: MutableStateFlow<Lazy<Throwable>?> = MutableStateFlow(null)
    val searchError = _searchError.asStateFlow()
     */

    private val _showsResult = MutableStateFlow<Result<List<Show>>>(Result.success(emptyList()))
    val showsResult = _showsResult.asStateFlow()

    private val _showsError = MutableStateFlow<Lazy<Throwable>?>(null)
    val showsError = _showsError.asStateFlow()

    fun getDefaultShows() {
        viewModelScope.launch {
            _loading.value = true
            val imdbList = defaultShowImdbMap.values.toList()
            getShowsUseCase(imdbList)
                .collect { result ->
                    _showsResult.value = result
                    /*
                    result.onSuccess { list ->

                    }.onFailure { throwable ->

                    }
                     */
                    _loading.value = false
                }
        }
    }

    /*
    fun searchShows(query: String) {
        viewModelScope.launch {
            _loading.value = true
            searchShowsUseCase(query)
                .collect { result ->
                    result.onSuccess { list ->
                        _searchResults.value = list
                        _searchError.value = null
                        Log.d("MainViewModel", "Success! list=$list")
                    }.onFailure { throwable ->
                        _searchResults.value = null
                        _searchError.value = lazy { throwable }
                        Log.d("MainViewModel", "Failure! throwable=$throwable")
                    }
                    _loading.value = false
                }
        }
    }
     */

    companion object {
        private val defaultShowImdbMap: Map<String, String> = mapOf(
            "Brooklyn Nine-Nine" to "tt2467372",
            "Cheers" to "tt0083399",
            "Friends" to "tt0108778",
            "Good Omens" to "tt1869454",
            "Seinfeld" to "tt0098904",
            "M*A*S*H" to "tt0068098",
            "The Good Place" to "tt4955642",
            "The Muppet Show" to "tt0074028",
            "The Orville" to "tt5691552",
            "WandaVision" to "tt9140560"
        )
    }

}