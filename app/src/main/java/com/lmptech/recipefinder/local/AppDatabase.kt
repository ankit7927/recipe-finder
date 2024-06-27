package com.lmptech.recipefinder.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lmptech.recipefinder.data.models.recipe.FavoriteRecipeModel
import com.lmptech.recipefinder.local.daos.FavoriteRecipeDao


@Database(entities = [FavoriteRecipeModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteRecipeDao(): FavoriteRecipeDao

    companion object {
        private var instance:AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "recipe_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}