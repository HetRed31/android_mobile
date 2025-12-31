package com.example.dessert_7_1.model

import androidx.annotation.DrawableRes

/**
 * [Dessert] is the data class to represent a dessert imageId, price, and startProductionAmount
 */
data class Dessert(
    @DrawableRes val imageId: Int,
    val price: Int,
    val startProductionAmount: Int
)
