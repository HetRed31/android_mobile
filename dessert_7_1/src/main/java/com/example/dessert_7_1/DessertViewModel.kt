package com.example.dessert_7_1

import androidx.lifecycle.ViewModel
import com.example.dessert_7_1.data.Datasource
import com.example.dessert_7_1.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {

    // Приватное изменяемое состояние пользовательского интерфейса. 
    // Только ViewModel может изменять это состояние.
    private val _uiState = MutableStateFlow(DessertUiState())

    // Публичное неизменяемое состояние пользовательского интерфейса. 
    // UI может только читать данные из этого StateFlow.
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

    // Эта функция вызывается, когда пользователь нажимает на десерт.
    fun onDessertClicked() {
        // Обновляем состояние uiState.
        _uiState.update { cupcakeUiState ->
            val dessertsSold = cupcakeUiState.dessertsSold + 1
            val nextDessert = determineDessertToShow(dessertsSold)
            // Создаем копию текущего состояния с обновленными значениями.
            cupcakeUiState.copy(
                dessertsSold = dessertsSold,
                revenue = cupcakeUiState.revenue + cupcakeUiState.currentDessertPrice,
                currentDessertPrice = nextDessert.price,
                currentDessertImageId = nextDessert.imageId
            )
        }
    }

    private fun determineDessertToShow(dessertsSold: Int): Dessert {
        var dessertToShow = Datasource.dessertList.first()
        for (dessert in Datasource.dessertList) {
            if (dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = dessert
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'''ll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who'''s "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }
        return dessertToShow
    }
}

// Класс данных, который представляет состояние пользовательского интерфейса (доход, проданные десерты и т.д.)
data class DessertUiState(
    val revenue: Int = 0,
    val dessertsSold: Int = 0,
    val currentDessertPrice: Int = Datasource.dessertList.first().price,
    val currentDessertImageId: Int = Datasource.dessertList.first().imageId,
)