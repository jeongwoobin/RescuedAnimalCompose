package com.example.presentation.screens.rescuedAnimalScreen.searchFilterScreen

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.presentation.navigation.graph.RescuedAnimalGraph
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.searchFilterScreen(navController: NavHostController) {
    composable<RescuedAnimalGraph.RescuedAnimal.SearchFilter>(typeMap = RescuedAnimalGraph.RescuedAnimal.SearchFilter.typeMap) {
        val viewModel = hiltViewModel<SearchFilterViewModel>()
        SearchFilterScreen(navController = navController,
            uiState = viewModel.uiState.collectAsStateWithLifecycle(
                lifecycleOwner = LocalLifecycleOwner.current
            ),
            onEventSent = viewModel::setEvent,
            effectFlow = viewModel.effect,
            onNavigationRequested = { navigationEffect ->
            },
            popBackStack = { filter ->
                filter?.let {
                    navController.previousBackStackEntry?.savedStateHandle?.apply {
                        set("resultFilter", Json.encodeToString(filter))
                    }
                }
                navController.popBackStack()
            })
    }
}
