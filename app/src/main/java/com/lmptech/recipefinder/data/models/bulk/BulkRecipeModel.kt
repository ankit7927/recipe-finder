package com.lmptech.recipefinder.data.models.bulk

import com.lmptech.recipefinder.data.models.recipe.RecipeModel

data class BulkRecipeModel(
    val aggregateLikes: Int,
    val analyzedInstructions: List<AnalyzedInstruction>,
    val approved: Int,
    val cheap: Boolean,
    val cookingMinutes: Int,
    val creditsText: String,
    val cuisines: List<String>,
    val dairyFree: Boolean,
    val diets: List<Any>,
    val dishTypes: List<String>,
    val extendedIngredients: List<ExtendedIngredient>,
    val gaps: String,
    val glutenFree: Boolean,
    val healthScore: Int,
    val id: Int,
    val image: String,
    val imageType: String,
    val instructions: String,
    val lowFodmap: Boolean,
    val nutrition: Nutrition,
    val occasions: List<Any>,
    val openLicense: Int,
    val originalId: Any,
    val preparationMinutes: Int,
    val pricePerServing: Double,
    val readyInMinutes: Int,
    val report: Any,
    val servings: Int,
    val sourceName: String,
    val sourceUrl: String,
    val spoonacularScore: Double,
    val spoonacularSourceUrl: String,
    val summary: String,
    val suspiciousDataScore: Int,
    val sustainable: Boolean,
    val tips: Tips,
    val title: String,
    val unknownIngredients: List<Any>,
    val userTags: List<Any>,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val weightWatcherSmartPoints: Int,
    val winePairing: WinePairing,
    val similarRecipes: List<RecipeModel>?
)