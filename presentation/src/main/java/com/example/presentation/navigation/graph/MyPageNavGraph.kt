package com.example.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.presentation.screens.myPage.MyPageScreen

fun NavGraphBuilder.myPageNavGraph(navController: NavHostController) {

    navigation<HomeGraph.MyPage>(
        startDestination = MyPageGraph.MyPage
    ) {
        composable<MyPageGraph.MyPage> {
            MyPageScreen(navController = navController)
        }
    }
}