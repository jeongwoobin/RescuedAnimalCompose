package com.example.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.presentation.screens.home.HomeScreen

@Composable
fun RootNavGraph(navController: NavHostController, startDestination: String) {

    NavHost(
        navController = navController, route = Graph.ROOT, startDestination = startDestination
    ) {
        composable(route = Graph.HOME) {
            HomeScreen(navController = navController)
        }
    }
}