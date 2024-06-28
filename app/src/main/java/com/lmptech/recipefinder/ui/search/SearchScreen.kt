package com.lmptech.recipefinder.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lmptech.recipefinder.data.models.recipe.RecipeModel
import com.lmptech.recipefinder.data.models.search.RecipeResult
import com.lmptech.recipefinder.ui.AppViewModelProvider
import com.lmptech.recipefinder.ui.home.HorizontalCard
import com.lmptech.recipefinder.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch


object SearchDestination: NavigationDestination{
    override val route: String
        get() = "search"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.factory),
    navigateBack:()->Unit,
    navigateToRecipe: (Int)->Unit
) {
    val sheetVisible = remember {
        mutableStateOf(false)
    }

    val selectedRecipe = remember {
        mutableStateOf<RecipeResult?>(null)
    }

    val coroutine = rememberCoroutineScope()
    val sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val searchUiState by searchViewModel.searchUiState.collectAsState()

    Scaffold (
        topBar = {
            SearchBar(
                leadingIcon = {
                    IconButton(onClick = { navigateBack.invoke() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                placeholder = { Text(text = "Search recipe") },
                query = searchUiState.query,
                onQueryChange = { searchViewModel.onQueryChange(it) },
                onSearch = {},
                active = false,
                onActiveChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {}
        }
    ) { paddingValues ->
        if (searchUiState.loading) {
            CircularProgressIndicator()
        } else if (searchUiState is SearchState.SearchWithOutResult) {
            Text(text = searchUiState.error)
        } else {
            val data = (searchUiState as SearchState.SearchWithResult)
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(top = 12.dp)
            ) {
                items(items = data.result, key = { it.id }) {
                    ListItem(leadingContent = {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart, contentDescription = ""
                        )
                    },
                        headlineContent = { Text(text = it.title) },
                        modifier = Modifier.clickable {
                            selectedRecipe.value = it
                            sheetVisible.value = true
                            coroutine.launch {
                                sheetState.show()
                            }
                        })
                }
            }

            if (sheetVisible.value) {
                ModalBottomSheet(
                    dragHandle = {},
                    modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                    onDismissRequest = {
                        coroutine.launch {
                            sheetVisible.value = false
                            sheetState.hide()
                        }
                    },
                    sheetState = sheetState
                ) {
                    SheetContent(recipeModel = selectedRecipe.value!!, navigateToRecipe = {
                        coroutine.launch {
                            sheetState.hide()
                            sheetVisible.value = false
                            navigateToRecipe.invoke(it)
                        }
                    })
                }
            }
        }
    }




}