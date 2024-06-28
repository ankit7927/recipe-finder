package com.lmptech.recipefinder.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lmptech.recipefinder.data.models.random.Recipe
import com.lmptech.recipefinder.data.models.recipe.RecipeModel

@Composable
fun VerticalCard(recipeModel: Recipe, recipeClicked: (Int)->Unit) {

    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .size(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { recipeClicked.invoke(recipeModel.id) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(recipeModel.image)
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
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.8f)
                        ),
                        startY = 300f
                    )
                )
        )

        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
            Text(
                text = recipeModel.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(bottom = 4.dp),
                overflow = TextOverflow.Clip,
                maxLines = 2
            )
            Text(
                text = "ready in ${recipeModel.readyInMinutes} minutes",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                ),
                maxLines = 1
            )
        }


    }
}


@Composable
fun HorizontalCard(recipeModel: Recipe, recipeClicked: (Int)->Unit) {

    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shape = RoundedCornerShape(16.dp),
        onClick = {recipeClicked.invoke(recipeModel.id)}
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(recipeModel.image)
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
                    text = recipeModel.title,
                    style = TextStyle(fontSize = 18.sp,fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(bottom = 4.dp),
                    maxLines = 2
                )
                Text(text = "ready in ${recipeModel.readyInMinutes} minutes",
                    style = TextStyle(fontSize = 12.sp, color = Color.DarkGray),)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
}

