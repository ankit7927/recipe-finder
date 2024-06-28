package com.lmptech.recipefinder.ui.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmptech.recipefinder.data.models.bulk.BulkRecipeModel
import com.lmptech.recipefinder.data.models.recipe.FavoriteRecipeModel
import com.lmptech.recipefinder.data.repositories.FavoriteRepository
import com.lmptech.recipefinder.data.repositories.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface RecipeState {
    val loading:Boolean
    val errorMsg:String

    data class WithRecipeState (
        override val loading: Boolean,
        override val errorMsg: String,
        val recipeModel: BulkRecipeModel
    ) : RecipeState

    data class WithOutRecipeState (
        override val loading: Boolean,
        override val errorMsg: String,
    ) : RecipeState
}

private data class RecipeViewModelState(
    val loading:Boolean = false,
    val recipeModel: BulkRecipeModel? = null,
    val errorMsg:String=""
) {
    fun toRecipeState ():RecipeState = if (recipeModel === null ) {
        RecipeState.WithOutRecipeState(loading, errorMsg)
    } else {
        RecipeState.WithRecipeState(loading, errorMsg, recipeModel)
    }
}

class RecipeViewModel(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private var recipeId: Int? = savedStateHandle[RecipeDestination.RECIPE_ID]

    private val recipeViewModelState: MutableStateFlow<RecipeViewModelState> = MutableStateFlow(
        RecipeViewModelState(loading = true)
    )

    val recipeUiState:StateFlow<RecipeState> = recipeViewModelState.map {
        it.toRecipeState()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, recipeViewModelState.value.toRecipeState())


    init {
        if (recipeId!==null) {
            getRecipeInformation(recipeId.toString())
        }
    }

    fun getRecipeInformation(recipeId:String) {
        viewModelScope.launch {
            val recipeById = recipeRepository.getRecipeInformation(recipeId)
            if (recipeById.isSuccessful && recipeById.body() !== null) {

                recipeViewModelState.emit(
                    RecipeViewModelState().copy(
                        recipeModel = recipeById.body()!!.first()
                    )
                )
            }
        }
    }


    fun addToFav(recipeModel: BulkRecipeModel){
        viewModelScope.launch {
            favoriteRepository.addFavoriteRecipe(FavoriteRecipeModel(recipeModel.id, recipeModel.title, recipeModel.image, recipeModel.readyInMinutes.toString()))
        }
    }

}