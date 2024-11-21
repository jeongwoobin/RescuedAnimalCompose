package com.example.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.presentation.screens.myPage.MyPageScreen

fun NavGraphBuilder.myPageNavGraph(navController: NavHostController) {

    navigation(
        startDestination = MyPageScreen.MyPage.route, route = Graph.MYPAGE
    ) {
        composable(MyPageScreen.MyPage.route) {
            MyPageScreen(navController = navController)
        }
    }
}

sealed class MyPageScreen(val route: String) {
    data object MyPage : MyPageScreen("MY_PAGE")
}