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

/**
 * A [ViewModel] used for business logic related to the initial list of shows presented
 * by this application.
 */
@HiltViewModel
class ShowListViewModel @Inject constructor(
    private val getShowsUseCase: GetShowsUseCase
) : ViewModel() {

    // region Properties

    /**
     * Tracks whether data is being loaded.
     */
    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    /**
     * The retrieved list of shows.
     */
    private val _shows = MutableStateFlow<List<Show>>(emptyList())
    val shows = _shows.asStateFlow()

    /**
     * A [Lazy] error, allowing the view to react a single time to changes.
     */
    private val _error = MutableStateFlow<Lazy<Throwable>?>(null)
    val error = _error.asStateFlow()

    // endregion Properties

    // region Methods

    /**
     * Gets the list of shows and sets loading, result & error information as appropriate.
     */
    fun getDefaultShows() {
        viewModelScope.launch {
            _loading.value = true
            val ids = defaultShows.values.toList()
            getShowsUseCase(ids)
                .collect { result ->
                    result.onSuccess {
                        _shows.value = it
                        _error.value = null
                    }.onFailure {
                        _error.value = lazy { it }
                    }
                    _loading.value = false
                }
        }
    }

    // endregion Methods

    companion object {

        // region Properties

        /**
         * A map containing names and IDs of a default list of shows
         * for display by this application.
         */
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

        // endregion Properties

    }

}