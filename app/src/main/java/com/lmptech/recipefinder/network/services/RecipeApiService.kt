package com.lmptech.recipefinder.network.services

import com.lmptech.recipefinder.data.models.bulk.BulkRecipeModel
import com.lmptech.recipefinder.data.models.recipe.RecipeModel
import com.lmptech.recipefinder.data.models.response.ResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RecipeApiService {

    @GET("/recipes")
    suspend fun getRecipes(
        @Query("_page") page: Int = 1,
        @Query("_per_page") perPage: Int = 10
    ): Response<ResponseModel>

    @GET("/recipes")
    suspend fun getRecipes2(
        @Query("_page") page: Int = 2,
        @Query("_per_page") perPage: Int = 10
    ): Response<ResponseModel>

    @GET("/bulk/{id}")
    suspend fun getRecipeById(
        @Path("id") id: Int,
    ): Response<BulkRecipeModel>

    @GET("")
    suspend fun searchRecipe(

    )
}