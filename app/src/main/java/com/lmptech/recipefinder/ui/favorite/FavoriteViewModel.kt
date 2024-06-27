package com.lmptech.recipefinder.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmptech.recipefinder.data.models.recipe.FavoriteRecipeModel
import com.lmptech.recipefinder.data.repositories.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class FavoriteState(
    val favoriteRecipes: List<FavoriteRecipeModel> = emptyList()
)

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    private val favoriteViewModelState: MutableStateFlow<FavoriteState> = MutableStateFlow(FavoriteState())

    val favoriteUiState: StateFlow<FavoriteState> = favoriteViewModelState

    init {
        viewModelScope.launch {
            favoriteRepository.getFavoriteRecipes().collectLatest {
                favoriteViewModelState.emit(FavoriteState(it))
            }
        }
    }

    fun addFavoriteRecipe(favoriteRecipeModel: FavoriteRecipeModel) {
        viewModelScope.launch {
            favoriteRepository.addFavoriteRecipe(favoriteRecipeModel)
        }
    }

    fun removeFavoriteRecipe(favoriteRecipeModel: FavoriteRecipeModel) {
        viewModelScope.launch {
            favoriteRepository.removeFavoriteRecipe(favoriteRecipeModel)
        }
    }
}