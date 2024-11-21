package com.example.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.presentation.screens.favoriteScreen.FavoriteScreen

fun NavGraphBuilder.favoriteNavGraph(navController: NavHostController) {

    navigation(
        startDestination = FavoriteScreen.Favorite.route, route = Graph.FAVORITE
    ) {
        composable(FavoriteScreen.Favorite.route) {
            FavoriteScreen(navController = navController)
        }
    }
}

sealed class FavoriteScreen(val route: String) {
    data object Favorite : FavoriteScreen("FAVORITE")
}