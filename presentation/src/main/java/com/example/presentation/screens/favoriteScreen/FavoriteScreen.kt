package com.example.presentation.screens.favoriteScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.SnackbarHostState
//import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
//import androidx.window.core.layout.WindowWidthSizeClass
import com.example.presentation.component.BaseScreen
import com.example.presentation.component.CustomPullToRefreshBox
import com.example.presentation.component.GoToTopFAB
import com.example.presentation.component.Header
import com.example.presentation.component.HDivider
import com.example.presentation.component.AnimalList
import com.example.presentation.component.LinearProgressBar
import com.example.presentation.component.VDivider
import com.example.presentation.screens.favoriteScreen.FavoriteViewModel
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
//    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyGridState()
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbar by favoriteViewModel.snackbarEvent.collectAsStateWithLifecycle()

    LaunchedEffect(snackbar) {
        snackbar.getContentIfNotHandled()?.let {
            snackbarHostState.showSnackbar(it, null)
        }
    }

    BaseScreen(
        snackbarHostState = snackbarHostState,
        loadingStateFlow = favoriteViewModel.resultState,
        loadingProgressBar = { LinearProgressBar() },
        fab = {
            GoToTopFAB(onClicked = {
                coroutineScope.launch {
                    // Scroll to the top of the list when the FAB is clicked
                    listState.animateScrollToItem(0)
                }
            })
        }) {
//        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT)
            Column {
                Header()
                HDivider(modifier = Modifier.padding(horizontal = 20.dp))
                CustomPullToRefreshBox(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 20.dp),
                    onRefresh = { favoriteViewModel.selectFavoriteAnimal() }) {
                    AnimalList(
                        modifier = Modifier.fillMaxSize(),
                        listState = listState,
                        itemListState = favoriteViewModel.favoriteAnimalList,
                        onLoadMore = { refresh ->
                            coroutineScope.launch {
                                favoriteViewModel.selectFavoriteAnimal()
                            }
                        },
                        itemClicked = { index, animal ->
                            coroutineScope.launch {
                                favoriteViewModel.deleteFavoriteAnimal(
                                    index = index,
                                    animal = animal
                                )
                            }
                        }
                    )
                }
            }
//        else
//            Row {
//                Header(
//                    route = Screen.FavoriteScreen,
//                    rightButtonClicked = {
//                        navController.navigate(Screen.RescuedAnimalScreen.route)
//                    })
//                VDivider(modifier = Modifier.padding(vertical = 20.dp))
//                CustomPullToRefreshBox(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 20.dp),
//                    onRefresh = { favoriteViewModel.selectFavoriteAnimal() }) {
//                    AnimalList(
//                        modifier = Modifier.fillMaxSize(),
//                        listState = listState,
//                        itemListState = favoriteViewModel.favoriteAnimalList,
//                        onLoadMore = { refresh ->
//                            coroutineScope.launch {
//                                favoriteViewModel.selectFavoriteAnimal()
//                            }
//                        },
//                        itemClicked = { index, animal ->
//                            coroutineScope.launch {
////                                favoriteViewModel.insertFavoriteAnimal(
////                                    index = index,
////                                    animal = animal
////                                )
//                            }
//                        }
//                    )
//                }
//            }
    }
}