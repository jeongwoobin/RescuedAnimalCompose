package com.example.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun HomeNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = RescuedAnimalScreen.RescuedAnimal.route
    ) {

        rescuedAnimalNavGraph(navController = navController)

        favoriteNavGraph(navController = navController)

        myPageNavGraph(navController = navController)
    }
}