package com.lmptech.recipefinder.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lmptech.recipefinder.ui.favorite.FavoriteDestination
import com.lmptech.recipefinder.ui.favorite.FavoriteScreen
import com.lmptech.recipefinder.ui.home.HomeDestination
import com.lmptech.recipefinder.ui.home.HomeScreen
import com.lmptech.recipefinder.ui.recipe.RecipeDestination
import com.lmptech.recipefinder.ui.recipe.RecipeScreen
import com.lmptech.recipefinder.ui.search.SearchDestination
import com.lmptech.recipefinder.ui.search.SearchScreen

@Composable
fun RecipeNavigationGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = HomeDestination.route) {

        composable(route = HomeDestination.route) {
            HomeScreen(navigateToRecipe = {
                navController.navigate("${RecipeDestination.route}/$it")
            }, navigateToSearch = {
                navController.navigate(SearchDestination.route)
            }, navigateToFavorite = {navController.navigate(FavoriteDestination.route)})
        }

        composable(route = FavoriteDestination.route) {
            FavoriteScreen(
                navigateToRecipe = { navController.navigate("${RecipeDestination.route}/$it") },
                navigateBack = { navController.popBackStack() })
        }

        composable(route = RecipeDestination.routeWithArg,
            arguments = listOf(navArgument(name = RecipeDestination.RECIPE_ID) {
                type = NavType.IntType
            })
        ) {
            RecipeScreen(
                backClicked = {navController.popBackStack()}
            )
        }

        composable(route=SearchDestination.route) {
            SearchScreen(navigateBack = {
                navController.popBackStack()
            }, navigateToRecipe = {
                navController.navigate("${RecipeDestination.route}/$it")
            })
        }


    }
}