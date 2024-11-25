package com.example.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.presentation.screens.home.HomeScreen

@Composable
fun RootNavGraph(navController: NavHostController, startDestination: RootGraph) {

    NavHost(
        navController = navController, startDestination = startDestination
    ) {
        composable<RootGraph.Home> {
            HomeScreen()
        }
        composable<RootGraph.Auth> {
//            Text("SignIn")
        }
    }
}