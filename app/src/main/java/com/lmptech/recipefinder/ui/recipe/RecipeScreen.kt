package com.lmptech.recipefinder.ui.recipe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lmptech.recipefinder.data.models.bulk.BulkRecipeModel
import com.lmptech.recipefinder.data.models.bulk.ExtendedIngredient
import com.lmptech.recipefinder.data.models.recipe.RecipeModel
import com.lmptech.recipefinder.ui.AppViewModelProvider
import com.lmptech.recipefinder.ui.navigation.NavigationDestination

object RecipeDestination : NavigationDestination {
    const val recipeId: String = "recipeId"
    override val route: String = "recipe"

    val routeWithArg: String = "$route/{$recipeId}"
}

@Composable
fun RecipeScreen(
    recipeViewModel: RecipeViewModel = viewModel(factory = AppViewModelProvider.factory),
    backClicked: () -> Unit
) {
    val recipeState by recipeViewModel.recipeUiState.collectAsState()

    if (recipeState.loading) {
        CircularProgressIndicator()
    } else if (recipeState is RecipeState.WithOutRecipeState) {
        Scaffold {
            Text(text = "Failed to get recipe", modifier = Modifier.padding(it))
        }
    } else {
        val recipeModel:BulkRecipeModel = (recipeState as RecipeState.WithRecipeState).recipeModel

        Scaffold {
            LazyColumn(modifier = Modifier.padding(it)) {
                item {
                    RecipeTop(
                        recipeName = recipeModel.title,
                        image = recipeModel.image,
                        onBackClick = backClicked,
                        addTOFav = {
                            recipeViewModel.addToFav(recipeModel = recipeModel)
                        }
                    )
                    StaticsRow(
                        readyIn = recipeModel.readyInMinutes,
                        serving = recipeModel.servings,
                        priceServing = recipeModel.pricePerServing
                    )
                }

                item {
                    Text(
                        text = "Ingredients",
                        style = TextStyle(fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray),
                        modifier = Modifier.padding(16.dp)
                    )
                    IngredientRow(ingredient = recipeModel.extendedIngredients)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                item {
                    InfoCard(title = "Instructions", text = recipeModel.instructions, expandable = false)
                    InfoCard(title = "Quick summary", text = recipeModel.summary, expandable = false)
                }
            }
        }
    }
}


@Composable
fun RecipeTop(recipeName:String, image: String, onBackClick:()->Unit, addTOFav:()->Unit) {
    Box (modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .background(color = Color.Gray)
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, Color.Black.copy(alpha = 0.8f)
                        ), startY = 300f
                    )
                )
        ) {
            Column (verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()){
                Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="")
                    }

                    FilledTonalIconButton(onClick =addTOFav) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription ="")
                    }
                }

                Text(
                    text = recipeName,
                    style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(16.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


@Composable
fun StaticsRow(readyIn:Int?, serving:Int?, priceServing:Double?) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp), horizontalArrangement = Arrangement.SpaceAround) {
        if (readyIn != null) {
            StaticCard(state = "Ready in", value = "$readyIn min")
        }

        if (serving != null) {
            StaticCard(state = "Serving", value = "$serving")
        }
        if (priceServing != null) {
            StaticCard(state = "Price/Serving", value = "$priceServing")
        }
    }
}


@Composable
fun StaticCard(state:String, value: String) {
    Surface(
        modifier = Modifier
            .width(Dp.Infinity)
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(12.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = state)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = value,
                style = TextStyle(color = Color.Red, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun IngredientRow(ingredient:List<ExtendedIngredient>) {
    LazyRow {
        items(items = ingredient){
            Ingredient(image = it.image, name = it.name)
        }
    }
}

@Composable
fun Ingredient(image:String, name:String) {
    Column(modifier = Modifier
        .padding(horizontal = 12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(60.dp))
                .clip(RoundedCornerShape(60.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name, style = TextStyle(fontWeight = FontWeight.SemiBold, color = Color.DarkGray), maxLines = 2)
    }
}


@Composable
fun InfoCard(title:String, text:String, expandable: Boolean = true, maxLines:Int = Int.MAX_VALUE) {
    var expanded by remember {
        mutableStateOf(!expandable)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = if (expandable) Color.LightGray else MaterialTheme.colorScheme.surface)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            )

            if (expandable) {
                IconButton(onClick = {
                    expanded = !expanded
                }) {
                    if (expanded)
                        Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "")
                    else
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "")
                }
            }
        }

        AnimatedVisibility(visible = expanded) {
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.DarkGray
                ),
                maxLines = maxLines
            )
        }
    }
}

