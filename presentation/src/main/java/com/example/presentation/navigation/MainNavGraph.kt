package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.example.presentation.screens.favoriteScreen.favoriteScreenRoute
import com.example.presentation.screens.myPage.myPageScreenRoute
import com.example.presentation.screens.rescuedAnimalScreen.rescuedAnimalScreenRoute

@Composable
fun MainNavGraph(navController: NavHostController, startDestination: String) {

    NavHost(navController = navController, startDestination = startDestination) {

        navigation(
            startDestination = Screen.RescuedAnimalScreen.route,
            route = AuthScreen.Auth.route
        ) {
            composable(AuthScreen.Login.route) {
                LoginScreen(
                    loginAction = {
                        navController.navigate(MainScreen.Home.route) {
                            popUpTo(AuthScreen.Auth.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(AuthScreen.SignUp.route) {
                SignUpScreen()
            }
        }
        // RescuedAnimalScreen
        rescuedAnimalScreenRoute(navController = navController)

        // favoriteScreen
        favoriteScreenRoute(navController = navController)

        // myPageScreen
        myPageScreenRoute(navController = navController)
    }
}