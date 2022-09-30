package com.codepunk.credlychallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codepunk.credlychallenge.domain.model.Show
import com.codepunk.credlychallenge.domain.usecase.GetShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(
    val getShowUseCase: GetShowUseCase
) : ViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _showResult = MutableStateFlow<Result<Show?>>(Result.success(null))
    val showResult = _showResult.asStateFlow()

    private val _showError = MutableStateFlow<Lazy<Throwable>?>(null)
    val showError = _showError.asStateFlow()

    fun getCast(showId: Int) {
        viewModelScope.launch {
            _loading.value = true
            getShowUseCase(showId)
                .collect { result ->
                    _showResult.value = result
                    _loading.value = false
                }
        }
    }

}