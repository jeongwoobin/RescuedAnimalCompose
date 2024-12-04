package com.example.presentation.navigation.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.domain.entity.Animal
import com.example.presentation.screens.animalDetailScreen.AnimalDetailContract
import com.example.presentation.screens.animalDetailScreen.AnimalDetailScreen
import com.example.presentation.screens.animalDetailScreen.AnimalDetailViewModel
import com.example.presentation.screens.animalDetailScreen.detailScreen
import com.example.presentation.screens.favoriteScreen.FavoriteContract
import com.example.presentation.screens.favoriteScreen.FavoriteScreen
import com.example.presentation.screens.favoriteScreen.FavoriteViewModel

fun NavGraphBuilder.favoriteNavGraph(navController: NavHostController) {

    navigation<HomeGraph.Favorite>(
        startDestination = FavoriteGraph.Favorite
    ) {
        composable<FavoriteGraph.Favorite> {
            val viewModel = hiltViewModel<FavoriteViewModel>()
            FavoriteScreen(navController = navController,
                uiState = viewModel.uiState.collectAsStateWithLifecycle(
                    lifecycleOwner = LocalLifecycleOwner.current
                ),
                onEventSent = viewModel::setEvent,
                effectFlow = viewModel.effect,
                onNavigationRequested = { navigationEffect ->
                    when (navigationEffect) {
                        is FavoriteContract.Effect.Navigation.ToDetail -> navController.navigateToFavoriteDetail(
                            animal = navigationEffect.animal
                        )

                        is FavoriteContract.Effect.Navigation.ToFilter -> {}
                    }
                })
        }

        detailScreen(navController)
    }
}

fun NavHostController.navigateToFavoriteDetail(animal: Animal) {
    navigate(DetailGraph.Detail(animal = animal))
}