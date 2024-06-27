package com.lmptech.recipefinder.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lmptech.recipefinder.ui.AppViewModelProvider
import com.lmptech.recipefinder.ui.navigation.NavigationDestination


object HomeDestination : NavigationDestination {
    override val route: String
        get() = "home"
}


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.factory),
    navigateToRecipe:(Int)->Unit,
    navigateToSearch:()->Unit,
    navigateToFavorite: () -> Unit
) {

    val uiState by homeViewModel.homeUiState.collectAsState()

    Scaffold { paddingValues ->
        LazyColumn(modifier = Modifier
            .padding(paddingValues)) {
            item {
                TopSection(
                    navigateToSearch = navigateToSearch,
                    navigateToFavorite=navigateToFavorite
                )
            }

            item {
                Text(
                    text = "Popular Recipes",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }

            item {
                LazyRow {
                    items(items = uiState.recipes) { recipeModel ->
                        VerticalCard(recipeModel = recipeModel, recipeClicked = {navigateToRecipe.invoke(it)})
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "All Recipes",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.padding(horizontal = 16.dp,vertical = 10.dp)
                )
            }

            items(items = uiState.recipes) { recipeModel ->
                HorizontalCard(recipeModel = recipeModel, recipeClicked = {navigateToRecipe.invoke(it)})
            }
        }
    }

}

@Composable
fun TopSection(
    navigateToSearch: () -> Unit,
    navigateToFavorite: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {

        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(
                    text = "👋 Hello User",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Discover testy and healthy foods",
                    style = TextStyle(color = Color.DarkGray, fontSize = 16.sp)
                )
            }

            IconButton(onClick = navigateToFavorite) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "")
            }
        }

        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = CircleShape,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .height(48.dp)
                .clickable {
                    navigateToSearch.invoke()
                },
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
                BasicTextField(
                    enabled = false,
                    value = "searchQuery.value",
                    onValueChange = {  },
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    singleLine = true,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Search any recipe",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}