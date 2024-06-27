package com.lmptech.recipefinder.ui.favorite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lmptech.recipefinder.data.models.recipe.FavoriteRecipeModel
import com.lmptech.recipefinder.ui.AppViewModelProvider
import com.lmptech.recipefinder.ui.navigation.NavigationDestination

object FavoriteDestination:NavigationDestination {
    override val route: String
        get() = "favorite"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    favoriteViewModel: FavoriteViewModel = viewModel(factory = AppViewModelProvider.factory),
    navigateToRecipe: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    val state by favoriteViewModel.favoriteUiState.collectAsState()

    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "Favorite Recipes") }, navigationIcon = { IconButton(
                onClick = navigateBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }})
        }
    ) { paddingValues ->
        Box (modifier = Modifier.padding(paddingValues)){
            LazyColumn (modifier = Modifier.fillMaxWidth()) {
                items(items = state.favoriteRecipes, key = {it.recipeId}) {
                    FavCards(favoriteRecipeModel = it, navigateToRecipe=navigateToRecipe)
                }
            }
        }

    }
}

@Composable
fun FavCards(favoriteRecipeModel: FavoriteRecipeModel, navigateToRecipe:(Int)->Unit) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shape = RoundedCornerShape(16.dp),
        onClick = {navigateToRecipe.invoke(favoriteRecipeModel.recipeId)}
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(favoriteRecipeModel.image)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp))
            )
            Column (modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                Text(
                    text = favoriteRecipeModel.title,
                    style = TextStyle(fontSize = 18.sp,fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(bottom = 4.dp),
                    maxLines = 2
                )
                Text(
                    text = "ready in ${favoriteRecipeModel.description} minutes",
                    style = TextStyle(fontSize = 12.sp, color = Color.DarkGray),
                )
            }
        }
    }
}