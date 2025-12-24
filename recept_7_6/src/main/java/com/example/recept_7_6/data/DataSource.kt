package com.example.recept_7_6.data

import com.example.recept_7_6.R
import com.example.recept_7_6.model.Recipe
import com.example.recept_7_6.model.RecipeCategory

object DataSource {
    val recipes: List<Recipe> = listOf(
        Recipe(id = 1, titleResourceId = R.string.dessert_1, detailsResourceId = R.string.details_1, imageResourceId = R.drawable.dessert1, category = RecipeCategory.DESSERT),
        Recipe(id = 2, titleResourceId = R.string.dessert_2, detailsResourceId = R.string.details_2, imageResourceId = R.drawable.dessert2, category = RecipeCategory.DESSERT),
        Recipe(id = 3, titleResourceId = R.string.dessert_3, detailsResourceId = R.string.details_3, imageResourceId = R.drawable.dessert3, category = RecipeCategory.DESSERT),
        Recipe(id = 4, titleResourceId = R.string.main_course_1, detailsResourceId = R.string.details_4, imageResourceId = R.drawable.dinner3, category = RecipeCategory.MAIN), //паста
        Recipe(id = 5, titleResourceId = R.string.main_course_2, detailsResourceId = R.string.details_5, imageResourceId = R.drawable.dinner1, category = RecipeCategory.MAIN), //стейк
        Recipe(id = 6, titleResourceId = R.string.main_course_3, detailsResourceId = R.string.details_6, imageResourceId = R.drawable.dinner4, category = RecipeCategory.MAIN),
        Recipe(id = 7, titleResourceId = R.string.main_course_4, detailsResourceId = R.string.details_7, imageResourceId = R.drawable.dinner2, category = RecipeCategory.MAIN),
        Recipe(id = 8, titleResourceId = R.string.soup_1, detailsResourceId = R.string.details_8, imageResourceId = R.drawable.lunch1, category = RecipeCategory.SOUP),
        Recipe(id = 9, titleResourceId = R.string.soup_2, detailsResourceId = R.string.details_9, imageResourceId = R.drawable.appetizer1, category = RecipeCategory.SOUP),
        Recipe(id = 10, titleResourceId = R.string.breakfast_1, detailsResourceId = R.string.details_10, imageResourceId = R.drawable.breakfast1, category = RecipeCategory.BREAKFAST),
        Recipe(id = 11, titleResourceId = R.string.breakfast_2, detailsResourceId = R.string.details_11, imageResourceId = R.drawable.breakfast2, category = RecipeCategory.BREAKFAST),
        Recipe(id = 12, titleResourceId = R.string.breakfast_3, detailsResourceId = R.string.details_12, imageResourceId = R.drawable.breakfast3, category = RecipeCategory.BREAKFAST),
        Recipe(id = 13, titleResourceId = R.string.salad_1, detailsResourceId = R.string.details_13, imageResourceId = R.drawable.lunch2, category = RecipeCategory.SALAD),
        Recipe(id = 14, titleResourceId = R.string.salad_2, detailsResourceId = R.string.details_14, imageResourceId = R.drawable.lunch4, category = RecipeCategory.SALAD),
        Recipe(id = 15, titleResourceId = R.string.salad_3, detailsResourceId = R.string.details_15, imageResourceId = R.drawable.launch5, category = RecipeCategory.SALAD),
        Recipe(id = 16, titleResourceId = R.string.drink_1, detailsResourceId = R.string.details_16, imageResourceId = R.drawable.beverage1, category = RecipeCategory.DRINK),
        Recipe(id = 17, titleResourceId = R.string.drink_2, detailsResourceId = R.string.details_17, imageResourceId = R.drawable.beverage2, category = RecipeCategory.DRINK),
        Recipe(id = 18, titleResourceId = R.string.drink_3, detailsResourceId = R.string.details_18, imageResourceId = R.drawable.beverage3, category = RecipeCategory.DRINK)
    )

    val defaultRecipe: Recipe = recipes[0]

    private val mainPageRecipes = listOf(
        Recipe(id = 100, titleResourceId = R.string.main_courses_category, detailsResourceId = R.string.default_string, imageResourceId = R.drawable.dinner1, category = RecipeCategory.MAIN),
        Recipe(id = 101, titleResourceId = R.string.desserts_category, detailsResourceId = R.string.default_string, imageResourceId = R.drawable.dessert1, category = RecipeCategory.DESSERT),
        Recipe(id = 102, titleResourceId = R.string.soups_category, detailsResourceId = R.string.default_string, imageResourceId = R.drawable.appetizer1, category = RecipeCategory.SOUP),
        Recipe(id = 103, titleResourceId = R.string.salads_category, detailsResourceId = R.string.default_string, imageResourceId = R.drawable.appetizer2, category = RecipeCategory.SALAD),
        Recipe(id = 104, titleResourceId = R.string.drinks_category, detailsResourceId = R.string.default_string, imageResourceId = R.drawable.beverage1, category = RecipeCategory.DRINK),
        Recipe(id = 105, titleResourceId = R.string.breakfasts_category, detailsResourceId = R.string.default_string, imageResourceId = R.drawable.breakfast1, category = RecipeCategory.BREAKFAST)
    )

    fun getMain(): List<Recipe> {
        return mainPageRecipes
    }

    fun getRecipesForCategory(category: RecipeCategory): List<Recipe> {
        return recipes.filter { it.category == category }
    }
}