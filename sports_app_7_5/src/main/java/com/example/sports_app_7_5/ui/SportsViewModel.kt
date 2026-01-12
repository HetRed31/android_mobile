package com.example.sports_app_7_5.ui

import androidx.lifecycle.ViewModel
import com.example.sports_app_7_5.data.LocalSportsDataProvider
import com.example.sports_app_7_5.model.Sport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class SportsViewModel : ViewModel() {

    // _uiState — это "приватная труба" с данными (MutableStateFlow).
    // Только эта ViewModel может записывать в неё новые данные.
    private val _uiState = MutableStateFlow(
        SportsUiState(
            // Загружаем начальный список видов спорта из провайдера данных
            sportsList = LocalSportsDataProvider.getSportsData(),
            // Выбираем первый спорт из списка как текущий (по умолчанию)
            currentSport = LocalSportsDataProvider.getSportsData().getOrElse(0) {
                LocalSportsDataProvider.defaultSport
            }
        )
    )

    val uiState: StateFlow<SportsUiState> = _uiState

    /**
     * Функция вызывается, когда пользователь нажимает на какой-то вид спорта в списке.
     */
    fun updateCurrentSport(selectedSport: Sport) {
        // .update — это безопасный способ обновить состояние.
        _uiState.update { currentState ->
            // .copy создает копию текущего состояния, но с новым выбранным спортом.
            currentState.copy(currentSport = selectedSport)
        }
    }

    /**
     * Функция для переключения на экран списка (используется на телефонах).
     */
    fun navigateToListPage() {
        _uiState.update { currentState ->
            // Меняем флаг isShowingListPage на true, чтобы UI показал список.
            currentState.copy(isShowingListPage = true)
        }
    }

    /**
     * Функция для переключения на экран деталей (используется на телефонах).
     */
    fun navigateToDetailPage() {
        _uiState.update { currentState ->
            // Меняем флаг на false, чтобы UI скрыл список и показал подробности спорта.
            currentState.copy(isShowingListPage = false)
        }
    }
}


data class SportsUiState(
    val sportsList: List<Sport>,       // Список всех видов спорта
    val currentSport: Sport,           // Спорт, который выбран сейчас
    val isShowingListPage: Boolean = true // Флаг: показывать список (true) или детали (false)
)
