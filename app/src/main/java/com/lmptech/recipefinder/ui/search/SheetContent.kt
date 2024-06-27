package com.lmptech.recipefinder.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lmptech.recipefinder.data.models.recipe.ExtendedIngredient
import com.lmptech.recipefinder.data.models.recipe.RecipeModel
import com.lmptech.recipefinder.ui.home.HorizontalCard
import com.lmptech.recipefinder.ui.home.VerticalCard
import com.lmptech.recipefinder.ui.recipe.Ingredient
import com.lmptech.recipefinder.ui.recipe.IngredientRow

@Composable
fun SheetContent(recipeModel: RecipeModel, navigateToRecipe: (Int) -> Unit) {
    LazyColumn {
        item{
            Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 10.dp)) {
                TextButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="" )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = recipeModel.title)
                }

                FilledTonalIconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription ="" )
                }
            }
        }

        item {
            Accordion(component = {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(recipeModel.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .shadow(8.dp)
                )
            }, expandable = false)

            Accordion(title = "Ingredients", component = {
                LazyRow {
                    items(items = recipeModel.extendedIngredients) {
                        Ingredient(image = it.image, name = it.name)
                    }
                }
            })

            Accordion(title = "Instructions") {
                Text(text = recipeModel.instructions)
            }

            Accordion(title = "Quick Summary") {
                Text(text = recipeModel.summary)
            }
        }

        item {
            TextButton(
                onClick = {navigateToRecipe.invoke(recipeModel.id)},
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "See Full Information")
            }
        }
    }
}


@Composable
fun Accordion(
    title:String? = null,
    expandable: Boolean = true,
    component:@Composable () -> Unit,
) {
    var expanded by remember {
        mutableStateOf(!expandable)
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 6.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            if (title !== null) {
                Text(
                    text = title,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                )
            }
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
        AnimatedVisibility(visible = expanded, modifier = Modifier.padding(vertical = 12.dp)) {
            component()
        }
    }
}