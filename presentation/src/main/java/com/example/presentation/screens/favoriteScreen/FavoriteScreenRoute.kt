package com.example.presentation.screens.favoriteScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.navigation.Screen

fun NavGraphBuilder.favoriteScreenRoute(navController: NavController){
    composable(Screen.FavoriteScreen.route){
        FavoriteScreen(navController = navController)
    }
}