package com.example.recept_7_6.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Recipe(
    val id: Int,
    @StringRes val titleResourceId: Int,
    @StringRes val detailsResourceId: Int,
    @DrawableRes val imageResourceId: Int,
    val category: RecipeCategory
)
