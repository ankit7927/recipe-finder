package com.lmptech.recipefinder.data.repositories

import com.lmptech.recipefinder.data.models.recipe.FavoriteRecipeModel
import com.lmptech.recipefinder.local.daos.FavoriteRecipeDao
import kotlinx.coroutines.flow.Flow


interface FavoriteRepository {
    fun getFavoriteRecipes(): Flow<List<FavoriteRecipeModel>>
    suspend fun addFavoriteRecipe(favoriteRecipeModel: FavoriteRecipeModel)
    suspend fun removeFavoriteRecipe(favoriteRecipeModel: FavoriteRecipeModel)
}


class FavoriteRepositoryImpl(private val favoriteRecipeDao: FavoriteRecipeDao) :
    FavoriteRepository {
    override fun getFavoriteRecipes(): Flow<List<FavoriteRecipeModel>> = favoriteRecipeDao.getFavorite()

    override suspend fun addFavoriteRecipe(favoriteRecipeModel: FavoriteRecipeModel) =
        favoriteRecipeDao.addFavorite(favoriteRecipeModel)

    override suspend fun removeFavoriteRecipe(favoriteRecipeModel: FavoriteRecipeModel) =
        favoriteRecipeDao.removeFavorite(favoriteRecipeModel)

}