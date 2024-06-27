package com.lmptech.recipefinder.data.repositories

import com.lmptech.recipefinder.data.models.bulk.BulkRecipeModel
import com.lmptech.recipefinder.data.models.recipe.RecipeModel
import com.lmptech.recipefinder.data.models.response.ResponseModel
import com.lmptech.recipefinder.network.services.RecipeApiService
import retrofit2.Response


interface RecipeRepository {
    suspend fun getRecipes(): Response<ResponseModel>

    suspend fun getRecipes2(): Response<ResponseModel>

    suspend fun getRecipeById(id:Int):Response<BulkRecipeModel>
}

class RecipeImpl(private val recipeApiService: RecipeApiService) : RecipeRepository {
    override suspend fun getRecipes(): Response<ResponseModel> = recipeApiService.getRecipes()

    override suspend fun getRecipes2(): Response<ResponseModel> = recipeApiService.getRecipes2()

    override suspend fun getRecipeById(id: Int): Response<BulkRecipeModel> = recipeApiService.getRecipeById(id)

}