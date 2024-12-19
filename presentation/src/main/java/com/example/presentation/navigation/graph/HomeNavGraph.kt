package com.example.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun HomeNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController, startDestination = BottombarGraph.RescuedAnimal
    ) {

        rescuedAnimalNavGraph(navController = navController)

        favoriteNavGraph(navController = navController)

        myPageNavGraph(navController = navController)
    }
}