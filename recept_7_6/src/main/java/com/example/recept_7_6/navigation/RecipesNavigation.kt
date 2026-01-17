package com.example.recept_7_6.navigation

import kotlinx.serialization.Serializable

/**
 * Навигационные маршруты приложения
 */
sealed class RecipesRoute {
    @Serializable
    data object CategoryList : RecipesRoute()
    
    @Serializable
    data class RecipeList(val categoryName: String) : RecipesRoute()
    
    @Serializable
    data class RecipeDetails(val recipeId: Int) : RecipesRoute()
}
