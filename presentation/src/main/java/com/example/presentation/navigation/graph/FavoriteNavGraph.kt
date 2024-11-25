package com.example.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.presentation.screens.favoriteScreen.FavoriteScreen

fun NavGraphBuilder.favoriteNavGraph(navController: NavHostController) {

    navigation<HomeGraph.Favorite>(
        startDestination = FavoriteGraph.Favorite
    ) {
        composable<FavoriteGraph.Favorite> {
            FavoriteScreen(navController = navController)
        }
    }
}