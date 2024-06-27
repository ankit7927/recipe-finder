package com.lmptech.recipefinder.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lmptech.recipefinder.data.models.recipe.FavoriteRecipeModel
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteRecipeDao {
    @Insert
    suspend fun addFavorite(favoriteRecipeModel: FavoriteRecipeModel)

    @Delete
    suspend fun removeFavorite(favoriteRecipeModel: FavoriteRecipeModel)

    @Query("SELECT * FROM favorite_recipe")
    fun getFavorite(): Flow<List<FavoriteRecipeModel>>
}