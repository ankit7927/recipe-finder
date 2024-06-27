package com.lmptech.recipefinder.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmptech.recipefinder.data.models.recipe.RecipeModel
import com.lmptech.recipefinder.data.repositories.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HomeState(
    val query:String = "",
    val searchActive:Boolean = false,
    val loading: Boolean = false,
    val recipes:List<RecipeModel> = emptyList()
)


class HomeViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {
    private val homeState2:MutableStateFlow<HomeState> = MutableStateFlow(HomeState().copy(loading = true))

    val homeUiState:StateFlow<HomeState>
        get() = homeState2

    init {
        viewModelScope.launch {
            val data = recipeRepository.getRecipes()
            if (data.isSuccessful && data.body() != null) {
                homeState2.emit(HomeState().copy(loading = false, recipes = data.body()!!.data))
            }
        }
    }

    fun onSearchActiveChange(change:Boolean) {
        viewModelScope.launch {
            homeState2.emit(HomeState().copy(searchActive = change))
        }
    }
}

