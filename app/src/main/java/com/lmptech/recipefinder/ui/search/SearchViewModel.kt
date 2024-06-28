package com.lmptech.recipefinder.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmptech.recipefinder.data.models.recipe.RecipeModel
import com.lmptech.recipefinder.data.models.search.RecipeResult
import com.lmptech.recipefinder.data.repositories.RecipeRepository
import com.lmptech.recipefinder.ui.home.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


sealed interface SearchState {
    val query: String
    val error: String
    val loading: Boolean

    data class SearchWithResult(
        override val query: String,
        override val error: String,
        override val loading: Boolean,
        val result: List<RecipeResult>,
    ):SearchState

    data class SearchWithOutResult(
        override val query: String,
        override val error: String,
        override val loading: Boolean,
    ):SearchState
}


private data class SearchViewModelState(
    val query: String = "",
    val error: String = "",
    val loading: Boolean = false,
    val result: List<RecipeResult> = emptyList()
) {
    fun toSearchState():SearchState = if (result.isNotEmpty()) {
        SearchState.SearchWithResult(query, error, loading, result)
    } else {
        SearchState.SearchWithOutResult(query, error, loading)
    }
}

class SearchViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {
    private val searchViewModelState: MutableStateFlow<SearchViewModelState> = MutableStateFlow(
        SearchViewModelState()
    )

    val searchUiState: StateFlow<SearchState> = searchViewModelState.map {
        it.toSearchState()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, searchViewModelState.value.toSearchState())


    fun onQueryChange(query: String) {
        viewModelScope.launch {
            searchViewModelState.emit(SearchViewModelState().copy(query = query, loading = true))

            val response = recipeRepository.searchRecipe(query = query)

            if (response.isSuccessful && response.body() !== null) {
                searchViewModelState.emit(
                    SearchViewModelState().copy(
                        loading = false,
                        result = response.body()!!.results
                    )
                )
            } else {
                searchViewModelState.emit(
                    SearchViewModelState().copy(
                        loading = false,
                        error = response.message()
                    )
                )
            }
        }
    }
}