package com.example.recept_7_6.ui

import androidx.lifecycle.ViewModel
import com.example.recept_7_6.R
import com.example.recept_7_6.data.DataSource
import com.example.recept_7_6.model.Recipe
import com.example.recept_7_6.model.RecipeCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class RecipesUiState(
    val isShowingCategoryList: Boolean = true,
    val selectedRecipe: Recipe? = null,
    val currentTitle: Int = R.string.app_name // <-- ДОБАВЛЕНО: Ресурс ID для текущего заголовка
)

class RecipesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState

    fun navigateToListPage(category: RecipeCategory) {
        _uiState.update { currentState ->
            currentState.copy(
                isShowingCategoryList = false,
                selectedRecipe = null,
                currentTitle = category.titleResourceId // <-- ОБНОВЛЯЕМ ЗАГОЛОВОК НА НАЗВАНИЕ КАТЕГОРИИ
            )
        }
    }

    fun selectRecipe(recipe: Recipe) {
        _uiState.update { currentState ->
            currentState.copy(
                isShowingCategoryList = false,
                selectedRecipe = recipe,
                currentTitle = recipe.titleResourceId // <-- ОБНОВЛЯЕМ ЗАГОЛОВОК НА НАЗВАНИЕ БЛЮДА
            )
        }
    }

    fun navigateToCategoryList() {
        _uiState.update { currentState ->
            currentState.copy(
                isShowingCategoryList = true,
                selectedRecipe = null,
                currentTitle = R.string.app_name // <-- ВОЗВРАЩАЕМ НАЗВАНИЕ ПРИЛОЖЕНИЯ
            )
        }
    }

    fun navigateBack() {
        _uiState.update { currentState ->
            if (currentState.selectedRecipe != null) {
                // Если мы на странице деталей, возвращаемся к списку рецептов
                val category = DataSource.recipes.first { it.id == currentState.selectedRecipe.id }.category
                currentState.copy(
                    selectedRecipe = null,
                    currentTitle = category.titleResourceId // <-- ВОЗВРАЩАЕМ ЗАГОЛОВОК КАТЕГОРИИ
                )
            } else {
                // Если мы на списке рецептов, возвращаемся к списку категорий
                currentState.copy(
                    isShowingCategoryList = true,
                    currentTitle = R.string.app_name // <-- ВОЗВРАЩАЕМ НАЗВАНИЕ ПРИЛОЖЕНИЯ
                )
            }
        }
    }
}
