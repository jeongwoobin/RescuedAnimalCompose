package com.example.presentation.screens.animalDetailScreen

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.presentation.navigation.graph.DetailGraph

fun NavGraphBuilder.detailScreen(navController: NavHostController) {
    composable<DetailGraph.Detail>(typeMap = DetailGraph.Detail.typeMap) {
        val viewModel = hiltViewModel<AnimalDetailViewModel>()
        AnimalDetailScreen(navController = navController,
            uiState = viewModel.uiState.collectAsStateWithLifecycle(
                lifecycleOwner = LocalLifecycleOwner.current
            ),
            onEventSent = viewModel::setEvent,
            effectFlow = viewModel.effect,
            onNavigationRequested = { navigationEffect ->
//                    when (navigationEffect) {
//                        is AnimalDetailContract.Effect.Navigation.ToImage -> navController.navigateToFavoriteDetail(
//                            animal = navigationEffect.animal
//                        )
//                    }
            },
            popBackStack = {
                navController.popBackStack()
            })
    }
}
