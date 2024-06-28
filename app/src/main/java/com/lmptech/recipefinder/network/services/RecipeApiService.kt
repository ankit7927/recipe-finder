package com.lmptech.recipefinder.network.services

import com.lmptech.recipefinder.data.models.bulk.BulkRecipeModel
import com.lmptech.recipefinder.data.models.random.RandomRecipeModel
import com.lmptech.recipefinder.data.models.recipe.RecipeModel
import com.lmptech.recipefinder.data.models.response.ResponseModel
import com.lmptech.recipefinder.data.models.search.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RecipeApiService {

    @GET("recipes/random")
    suspend fun getRecipes(
        @Query("number") number: Int = 10
    ): Response<RandomRecipeModel>

    @GET("recipes/informationBulk")
    suspend fun getRecipeInformation(
        @Query("ids") ids: String,
    ): Response<List<BulkRecipeModel>>

    @GET("recipes/complexSearch")
    suspend fun searchRecipe(
        @Query("query") query: String
    ): Response<SearchResult>
}