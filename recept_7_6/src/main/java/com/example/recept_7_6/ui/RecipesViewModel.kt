package com.example.recept_7_6.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel для управления состоянием приложения рецептов.
 * 
 * После внедрения Jetpack Navigation, навигация управляется NavController,
 * поэтому ViewModel упрощена и может использоваться для хранения
 * общего состояния приложения, если потребуется в будущем.
 */
data class RecipesUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class RecipesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState

    // Здесь можно добавить методы для управления состоянием загрузки,
    // обработки ошибок или других глобальных состояний приложения
}
