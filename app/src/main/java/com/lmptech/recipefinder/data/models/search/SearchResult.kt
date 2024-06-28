package com.lmptech.recipefinder.data.models.search

data class SearchResult(
    val number: Int,
    val offset: Int,
    val results: List<RecipeResult>,
    val totalResults: Int
)