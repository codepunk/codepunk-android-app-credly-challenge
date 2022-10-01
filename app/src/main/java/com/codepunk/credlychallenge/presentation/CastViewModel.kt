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
import com.codepunk.credlychallenge.domain.model.CastEntry
import com.codepunk.credlychallenge.domain.usecase.GetCastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A [ViewModel] used for business logic related to the cast of a show.
 */
@HiltViewModel
class CastViewModel @Inject constructor(
    val getCastUseCase: GetCastUseCase
) : ViewModel() {

    // region Properties

    /**
     * Tracks whether data is being loaded.
     */
    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    /**
     * The retrieved cast associated with the supplied show ID.
     */
    private val _cast = MutableStateFlow<List<CastEntry>>(emptyList())
    val cast = _cast.asStateFlow()

    /**
     * A [Lazy] error, allowing the view to react a single time to changes.
     */
    private val _error = MutableStateFlow<Lazy<Throwable>?>(null)
    val error = _error.asStateFlow()

    // endregion Properties

    // region Methods

    /**
     * Gets the cast and sets loading, result & error information as appropriate.
     */
    fun getCast(showId: Int) {
        viewModelScope.launch {
            _loading.value = true
            getCastUseCase(showId)
                .collect { result ->
                    result.onSuccess {
                        _cast.value = it
                        _error.value = null
                    }.onFailure {
                        _error.value = lazy { it }
                    }
                    _loading.value = false
                }
        }
    }

    // endregion Methods

}
