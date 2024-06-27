package com.lmptech.recipefinder.data.models.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipe")
data class FavoriteRecipeModel(
    @PrimaryKey()
    val recipeId:Int,
    val title:String,
    val image:String,
    val description:String
)
