package com.example.presentation.navigation.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.domain.entity.Animal
import com.example.presentation.screens.animalDetailScreen.AnimalDetailScreen
import com.example.presentation.screens.rescuedAnimalScreen.RescuedAnimalContract
import com.example.presentation.screens.rescuedAnimalScreen.RescuedAnimalScreen
import com.example.presentation.screens.rescuedAnimalScreen.RescuedAnimalViewModel

fun NavGraphBuilder.rescuedAnimalNavGraph(navController: NavHostController) {

    navigation<HomeGraph.RescuedAnimal>(startDestination = RescuedAnimalGraph.RescuedAnimal) {
        composable<RescuedAnimalGraph.RescuedAnimal> {
            val viewModel = hiltViewModel<RescuedAnimalViewModel>()
            RescuedAnimalScreen(navController = navController,
                uiState = viewModel.uiState.collectAsStateWithLifecycle(
                    lifecycleOwner = LocalLifecycleOwner.current
                ),
                onEventSent = viewModel::setEvent,
                effectFlow = viewModel.effect,
                onNavigationRequested = { navigationEffect ->
                    when (navigationEffect) {
                        is RescuedAnimalContract.Effect.Navigation.ToDetail -> navController.navigateToRescuedAnimalDetail(
                            animal = navigationEffect.animal
                        )

                        is RescuedAnimalContract.Effect.Navigation.ToFilter -> {}
                    }
                })
        }

        composable<RescuedAnimalGraph.RescuedAnimal.RescuedAnimalDetail>(typeMap = RescuedAnimalGraph.RescuedAnimal.RescuedAnimalDetail.typeMap) {
            AnimalDetailScreen(navController = navController)
        }
    }
}


fun NavHostController.navigateToRescuedAnimalDetail(animal: Animal) {
    navigate(RescuedAnimalGraph.RescuedAnimal.RescuedAnimalDetail(animal = animal))
}
