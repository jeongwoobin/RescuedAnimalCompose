package com.example.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController, route = Graph.HOME, startDestination = Graph.RESCUEDANIMAL
    ) {

        rescuedAnimalNavGraph(navController = navController)

        favoriteNavGraph(navController = navController)

        myPageNavGraph(navController = navController)
    }
}