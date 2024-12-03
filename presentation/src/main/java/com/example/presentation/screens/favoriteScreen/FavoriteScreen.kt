package com.example.presentation.screens.favoriteScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.SnackbarHostState
//import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
//import androidx.window.core.layout.WindowWidthSizeClass
import com.example.presentation.base.BaseScreen
import com.example.presentation.component.CustomPullToRefreshBox
import com.example.presentation.component.GoToTopFAB
import com.example.presentation.component.Header
import com.example.presentation.component.HDivider
import com.example.presentation.component.AnimalList
import com.example.presentation.component.LinearProgressBar
import com.example.presentation.screens.rescuedAnimalScreen.RescuedAnimalContract
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    navController: NavController,
    uiState: State<FavoriteContract.State>,
    onEventSent: (event: FavoriteContract.Event) -> Unit,
    effectFlow: Flow<FavoriteContract.Effect>,
    onNavigationRequested: (navigationEffect: FavoriteContract.Effect.Navigation) -> Unit
) {
//    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyGridState()
    val snackbarHostState = remember { SnackbarHostState() }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        // refresh your data
        Logger.d("FavoriteScreen ON_RESUME")
        onEventSent(FavoriteContract.Event.InitData)
    }

    LaunchedEffect(effectFlow) {
        effectFlow.collectLatest { effect ->
            Logger.d("effectFlow: $effect")
            when (effect) {
                is FavoriteContract.Effect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.content, null)
                }

                is FavoriteContract.Effect.Navigation -> onNavigationRequested(effect)
            }
        }
    }

    BaseScreen(
        snackbarHostState = snackbarHostState,
        loadingState = uiState.value.loadingState == FavoriteContract.LoadingState.Loading,
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
                onRefresh = { onEventSent(FavoriteContract.Event.LoadMore(refresh = true)) }) {
                AnimalList(
                    modifier = Modifier.fillMaxSize(),
                    listState = listState,
                    itemList = uiState.value.favoriteAnimalListState,
                    onLoadMore = { refresh ->
                        onEventSent(FavoriteContract.Event.LoadMore(refresh = refresh))
                    },
                    itemClicked = { index, animal ->
                        onEventSent(FavoriteContract.Event.OnListItemClicked(animal))
//                        coroutineScope.launch {
//                            favoriteViewModel.deleteFavoriteAnimal(
//                                index = index,
//                                animal = animal
//                            )
//                        }
                    },
                    favoriteClicked = {index, animal ->
                        onEventSent(FavoriteContract.Event.OnItemFavoriteClicked(index, animal))
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