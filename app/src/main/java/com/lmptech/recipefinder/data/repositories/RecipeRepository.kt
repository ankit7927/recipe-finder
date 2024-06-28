package com.lmptech.recipefinder.data.repositories

import com.lmptech.recipefinder.data.models.bulk.BulkRecipeModel
import com.lmptech.recipefinder.data.models.random.RandomRecipeModel
import com.lmptech.recipefinder.data.models.recipe.RecipeModel
import com.lmptech.recipefinder.data.models.response.ResponseModel
import com.lmptech.recipefinder.data.models.search.SearchResult
import com.lmptech.recipefinder.network.services.RecipeApiService
import retrofit2.Response


interface RecipeRepository {
    suspend fun getRecipes(): Response<RandomRecipeModel>

    suspend fun getRecipeInformation(ids: String): Response<List<BulkRecipeModel>>

    suspend fun searchRecipe(query: String): Response<SearchResult>
}

class RecipeImpl(private val recipeApiService: RecipeApiService) : RecipeRepository {
    override suspend fun getRecipes(): Response<RandomRecipeModel> = recipeApiService.getRecipes()

    override suspend fun getRecipeInformation(ids: String): Response<List<BulkRecipeModel>> = recipeApiService.getRecipeInformation(ids)

    override suspend fun searchRecipe(query: String): Response<SearchResult> = recipeApiService.searchRecipe(query)
}