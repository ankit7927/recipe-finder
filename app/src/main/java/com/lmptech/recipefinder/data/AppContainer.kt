package com.lmptech.recipefinder.data

import android.content.Context
import com.lmptech.recipefinder.data.repositories.FavoriteRepository
import com.lmptech.recipefinder.data.repositories.FavoriteRepositoryImpl
import com.lmptech.recipefinder.data.repositories.RecipeImpl
import com.lmptech.recipefinder.data.repositories.RecipeRepository
import com.lmptech.recipefinder.local.AppDatabase
import com.lmptech.recipefinder.network.services.ApiServices
import com.lmptech.recipefinder.network.services.RecipeApiService


interface AppContainer {
    val recipeRepository: RecipeRepository
    val favoriteRepository: FavoriteRepository
}

class AppContainerImpl(
    private val context: Context
) : AppContainer {

    private val recipeApiService: RecipeApiService = ApiServices.recipeApiService

    override val recipeRepository: RecipeRepository
        get() = RecipeImpl(recipeApiService)

    override val favoriteRepository: FavoriteRepository
        get() = FavoriteRepositoryImpl(
            AppDatabase.getInstance(context = context).favoriteRecipeDao()
        )
}
