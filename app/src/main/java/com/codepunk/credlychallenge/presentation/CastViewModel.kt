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

@HiltViewModel
class CastViewModel @Inject constructor(
    val getCastUseCase: GetCastUseCase
) : ViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _castResult = MutableStateFlow<Result<List<CastEntry>>>(Result.success(emptyList()))
    val castResult = _castResult.asStateFlow()

    private val _showError = MutableStateFlow<Lazy<Throwable>?>(null)
    val showError = _showError.asStateFlow()

    fun getCast(showId: Int) {
        viewModelScope.launch {
            _loading.value = true
            getCastUseCase(showId)
                .collect { result ->
                    _castResult.value = result
                    _loading.value = false
                }
        }
    }

}