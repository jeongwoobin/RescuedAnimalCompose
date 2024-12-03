package com.example.presentation.screens.animalDetailScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
//import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.toRoute
import com.example.domain.entity.Animal
import com.example.presentation.base.BaseScreen
import com.example.presentation.component.LinearProgressBar
import com.example.presentation.navigation.graph.RescuedAnimalGraph
import com.example.presentation.screens.favoriteScreen.FavoriteContract
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow

@Composable
fun AnimalDetailScreen(
    navController: NavController,
    uiState: State<AnimalDetailContract.State>,
    onEventSent: (event: AnimalDetailContract.Event) -> Unit,
    effectFlow: Flow<AnimalDetailContract.Effect>,
    onNavigationRequested: (navigationEffect: AnimalDetailContract.Effect.Navigation) -> Unit
    ) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyGridState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(effectFlow) {
        effectFlow.collect { effect ->
            Logger.d("effectFlow: $effect")
            when (effect) {
                is AnimalDetailContract.Effect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.content, null)
                }

                is AnimalDetailContract.Effect.Navigation -> onNavigationRequested(effect)
            }
        }
    }

    BaseScreen(
        snackbarHostState = snackbarHostState,
        loadingState = uiState.value.loadingState == AnimalDetailContract.LoadingState.Loading,
        loadingProgressBar = { LinearProgressBar() },
    ) {
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable { navController.popBackStack() })
            Text(text = "Detail Screen id: ${uiState.value.animalState.desertionNo}")
        }
    }
}