package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.presentation.screens.favoriteScreen.favoriteScreenRoute
import com.example.presentation.screens.myPage.myPageScreenRoute
import com.example.presentation.screens.rescuedAnimalScreen.rescuedAnimalScreenRoute

@Composable
fun MyPageNavGraph(navController: NavHostController, startDestination: String) {

    NavHost(navController = navController, startDestination = startDestination) {

        // myPageScreen
        myPageScreenRoute(navController = navController)

        // findMyPet

    }
}