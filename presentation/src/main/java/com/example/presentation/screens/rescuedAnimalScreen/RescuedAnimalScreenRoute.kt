package com.example.presentation.screens.rescuedAnimalScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.navigation.Screen
import com.example.rescuedanimals.presentation.screens.rescuedAnimalScreen.RescuedAnimalScreen

fun NavGraphBuilder.rescuedAnimalScreenRoute(navController: NavController){
    composable(Screen.RescuedAnimalScreen.route){
        RescuedAnimalScreen(navController = navController)
    }
}