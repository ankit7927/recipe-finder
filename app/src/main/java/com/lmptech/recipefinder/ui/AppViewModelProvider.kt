package com.lmptech.recipefinder.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.lmptech.recipefinder.RecipeApplication
import com.lmptech.recipefinder.ui.favorite.FavoriteViewModel
import com.lmptech.recipefinder.ui.home.HomeViewModel
import com.lmptech.recipefinder.ui.recipe.RecipeViewModel
import com.lmptech.recipefinder.ui.search.SearchViewModel

object AppViewModelProvider {
    val factory = viewModelFactory {
        initializer {
            HomeViewModel(application().appContainer.recipeRepository)
        }

        initializer {
            RecipeViewModel(
                this.createSavedStateHandle(),
                application().appContainer.recipeRepository,
                application().appContainer.favoriteRepository
            )
        }

        initializer {
            SearchViewModel(application().appContainer.recipeRepository)
        }

        initializer {
            FavoriteViewModel(application().appContainer.favoriteRepository)
        }
    }
}

fun CreationExtras.application(): RecipeApplication = (this[APPLICATION_KEY] as RecipeApplication)
