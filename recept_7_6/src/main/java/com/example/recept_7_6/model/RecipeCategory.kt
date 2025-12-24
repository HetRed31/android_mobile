package com.example.recept_7_6.model

import androidx.annotation.StringRes
import com.example.recept_7_6.R

enum class RecipeCategory(
    @StringRes val titleResourceId: Int
) {
    MAIN(R.string.main_courses_category),
    DESSERT(R.string.desserts_category),
    SOUP(R.string.soups_category),
    SALAD(R.string.salads_category),
    DRINK(R.string.drinks_category),
    BREAKFAST(R.string.breakfasts_category),
}