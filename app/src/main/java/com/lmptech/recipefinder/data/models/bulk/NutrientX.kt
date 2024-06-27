package com.lmptech.recipefinder.data.models.bulk

data class NutrientX(
    val amount: Double,
    val name: String,
    val percentOfDailyNeeds: Double,
    val unit: String
)