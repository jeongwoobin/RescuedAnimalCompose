package com.example.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.presentation.screens.rescuedAnimalScreen.RescuedAnimalScreen

fun NavGraphBuilder.rescuedAnimalNavGraph(navController: NavHostController) {

    navigation(
        startDestination = RescuedAnimalScreen.RescuedAnimal.route, route = Graph.RESCUEDANIMAL
    ) {
        composable(RescuedAnimalScreen.RescuedAnimal.route) {
            RescuedAnimalScreen(navController = navController)
        }

//        composable(route = "${RescuedAnimalScreen.RescuedAnimal.route}/${RescuedAnimalScreen.RescuedAnimalDetail.route}",
//            arguments = listOf(navArgument("id") {
//                type = NavType.IntType
//                defaultValue = -1
//            })
//        ) {
//            RescuedAnimalDetailScreen(navController = navController)
//        }
    }
}

sealed class RescuedAnimalScreen(val route: String) {
    data object RescuedAnimal : RescuedAnimalScreen("RESCUED_ANIMAL")
    data object RescuedAnimalDetail : RescuedAnimalScreen("DETAIL?id={id}")
}