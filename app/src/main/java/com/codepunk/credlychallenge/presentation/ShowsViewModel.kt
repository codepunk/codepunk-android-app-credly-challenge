package com.codepunk.credlychallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codepunk.credlychallenge.domain.model.Show
import com.codepunk.credlychallenge.domain.usecase.GetShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val getShowsUseCase: GetShowsUseCase
) : ViewModel() {
    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _showsResult = MutableStateFlow<Result<List<Show>>>(Result.success(emptyList()))
    val showsResult = _showsResult.asStateFlow()

    private val _showsError = MutableStateFlow<Lazy<Throwable>?>(null)
    val showsError = _showsError.asStateFlow()

    fun getDefaultShows() {
        viewModelScope.launch {
            _loading.value = true
            val ids = defaultShows.values.toList()
            getShowsUseCase(ids)
                .collect { result ->
                    _showsResult.value = result
                    _loading.value = false
                }
        }
    }

    companion object {
        private val defaultShows: Map<String, Int> = mapOf(
            "Brooklyn Nine-Nine" to 49,
            "Cheers" to 553,
            "Friends" to 431,
            "Good Omens" to 28717,
            "Seinfeld" to 530,
            "M*A*S*H" to 665,
            "The Good Place" to 2790,
            "The Muppet Show" to 3288,
            "The Orville" to 20263,
            "WandaVision" to 41748
        )
    }

}