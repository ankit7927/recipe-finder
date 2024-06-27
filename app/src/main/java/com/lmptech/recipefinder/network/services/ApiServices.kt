package com.lmptech.recipefinder.network.services

import com.lmptech.recipefinder.network.NetworkModule

object ApiServices {
    val recipeApiService :RecipeApiService by lazy {
        NetworkModule.getInstance().create(RecipeApiService::class.java)
    }
}