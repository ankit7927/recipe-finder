package com.lmptech.recipefinder.data.models.response

import com.lmptech.recipefinder.data.models.recipe.RecipeModel

data class ResponseModel(
    val data: List<RecipeModel>,
    val first: Int,
    val items: Int,
    val last: Int,
    val next: Int,
    val pages: Int,
    val prev: Any
)