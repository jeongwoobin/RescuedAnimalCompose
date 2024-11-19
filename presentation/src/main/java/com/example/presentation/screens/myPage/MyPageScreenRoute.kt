package com.example.presentation.screens.myPage

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.navigation.Screen


fun NavGraphBuilder.myPageScreenRoute(navController: NavController){
    composable(Screen.MyPageScreen.route){
        MyPageScreen(navController = navController)
    }
}