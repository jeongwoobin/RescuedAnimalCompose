package com.example.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController, startDestination = HomeGraph.RescuedAnimal
    ) {

        rescuedAnimalNavGraph(navController = navController)

        favoriteNavGraph(navController = navController)

        myPageNavGraph(navController = navController)
    }
}