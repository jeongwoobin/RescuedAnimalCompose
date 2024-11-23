package com.example.presentation.navigation.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.presentation.screens.animalDetailScreen.AnimalDetailScreen
import com.example.presentation.screens.rescuedAnimalScreen.RescuedAnimalContract
import com.example.presentation.screens.rescuedAnimalScreen.RescuedAnimalScreen
import com.example.presentation.screens.rescuedAnimalScreen.RescuedAnimalViewModel
import kotlinx.serialization.Serializable

fun NavGraphBuilder.rescuedAnimalNavGraph(navController: NavHostController) {

    navigation(
        route = Graph.RESCUEDANIMAL,
        startDestination = RescuedAnimalScreen.RescuedAnimal.route
    ) {
        composable(route = RescuedAnimalScreen.RescuedAnimal.route) {
            val viewModel = hiltViewModel<RescuedAnimalViewModel>()
            RescuedAnimalScreen(
                navController = navController,
                uiState = viewModel.uiState.collectAsStateWithLifecycle(
                    lifecycleOwner = LocalLifecycleOwner.current
                ),
                onEventSent = viewModel::setEvent,
                effectFlow = viewModel.effect,
                onNavigationRequested = { navigationEffect ->
                    when (navigationEffect) {
                        is RescuedAnimalContract.Effect.Navigation.ToDetail ->
                            navController.navigateToRescuedAnimalDetail(id = navigationEffect.id)

                        is RescuedAnimalContract.Effect.Navigation.ToFilter -> {}
                    }
                })
        }

        composable<RescuedAnimalScreen.RescuedAnimalDetail> {
            AnimalDetailScreen(navController = navController)
        }

        composable(
            route = "${RescuedAnimalScreen.RescuedAnimal.route}/${RescuedAnimalScreen.RescuedAnimalDetail.route}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = "-1"
            })
        ) {
            AnimalDetailScreen(navController = navController)
        }
    }
}

sealed interface RescuedAnimalScreen {
    @Serializable
    data object RescuedAnimal : RescuedAnimalScreen

    @Serializable
    data class RescuedAnimalDetail(val id: String) : RescuedAnimalScreen
}


fun NavHostController.navigateToRescuedAnimalDetail(id: String) {
    navigate(RescuedAnimalScreen.RescuedAnimalDetail(id))
}
