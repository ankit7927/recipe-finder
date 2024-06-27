package com.lmptech.recipefinder.data.models.bulk

data class IngredientX(
    val amount: Double,
    val id: Int,
    val name: String,
    val nutrients: List<NutrientX>,
    val unit: String
)