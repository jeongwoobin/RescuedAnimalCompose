package com.example.presentation.screens.animalDetailScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
//import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.toRoute
import com.example.domain.entity.Animal
import com.example.presentation.base.BaseScreen
import com.example.presentation.component.LinearProgressBar
import com.example.presentation.navigation.graph.RescuedAnimalGraph

@Composable
fun AnimalDetailScreen(
    navController: NavController,
    animalDetailViewModel: AnimalDetailViewModel = hiltViewModel()
    ) {
    val snackbarHostState = remember { SnackbarHostState() }

    BaseScreen(
        snackbarHostState = snackbarHostState,
        loadingState = false,
        loadingProgressBar = { LinearProgressBar() },
    ) {
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable { navController.popBackStack() })
            Text(text = "Detail Screen id: ${animalDetailViewModel.animal.desertionNo}")
        }
    }
}