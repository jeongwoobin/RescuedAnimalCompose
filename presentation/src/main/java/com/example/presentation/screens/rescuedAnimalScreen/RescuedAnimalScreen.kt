package com.example.presentation.screens.rescuedAnimalScreen

//import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
//import androidx.window.core.layout.WindowWidthSizeClass
//import com.example.rescuedanimals.presentation.screens.rescuedAnimalScreen.RescuedAnimalViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.domain.entity.AnimalSearchFilter
import com.example.presentation.base.BaseScreen
import com.example.presentation.component.AnimalList
import com.example.presentation.component.CustomPullToRefreshBox
import com.example.presentation.component.GoToTopFAB
import com.example.presentation.component.HDivider
import com.example.presentation.component.Header
import com.example.presentation.component.LoadingProgressBar
import com.example.presentation.component.VectorIcon
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.json.JSONException

@Composable
fun RescuedAnimalScreen(
    navController: NavController,
    uiState: State<RescuedAnimalContract.State>,
    onEventSent: (event: RescuedAnimalContract.Event) -> Unit,
    effectFlow: Flow<RescuedAnimalContract.Effect>,
    onNavigationRequested: (navigationEffect: RescuedAnimalContract.Effect.Navigation) -> Unit
) {
//    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyGridState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        Logger.d("RescuedAnimalScreen Init")
        val filter: AnimalSearchFilter? =
            navController.currentBackStackEntry?.savedStateHandle?.remove<String>("resultFilter")
                ?.let { value ->
                    try {
                        val resultFilter = Json.decodeFromString<AnimalSearchFilter>(value)
                        Logger.d("resultFilter: $resultFilter")
                        resultFilter
                    } catch (e: Exception) {
                        Logger.e(e.toString())
                        null
                    }
                } ?: run {
                Logger.d("resultFilter is null")
                null
            }

        onEventSent(RescuedAnimalContract.Event.InitData(filter))
    }

    val listScrollOffset = remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }

//    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
//        // refresh your data
//        Logger.d("RescuedAnimalScreen ON_RESUME")
//        onEventSent(RescuedAnimalContract.Event.InitData)
//    }

    LaunchedEffect(effectFlow) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is RescuedAnimalContract.Effect.ShowSnackbar -> {
                    Logger.d("snackbar event: $effect")
                    snackbarHostState.showSnackbar(effect.content, null)
                }

                is RescuedAnimalContract.Effect.Navigation -> onNavigationRequested(effect)

                is RescuedAnimalContract.Effect.ScrollUp -> {
                    listState.animateScrollToItem(0)
                }
            }
        }
    }

    BaseScreen(snackbarHostState = snackbarHostState,
        loadingState = uiState.value.loadingState == RescuedAnimalContract.LoadingState.Loading,
        loadingProgressBar = { LoadingProgressBar() },
        fab = {
            if (listScrollOffset.value != 0) {
                GoToTopFAB(onClicked = {
                    onEventSent(RescuedAnimalContract.Event.OnFabClicked)
//                    coroutineScope.launch {
//                        // Scroll to the top of the list when the FAB is clicked
//                        listState.animateScrollToItem(0)
//                    }
                })
            }
        }) {
//        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT)
        Column {
            Header(title = "구조된 동물") {
                VectorIcon(
                    modifier = Modifier.clickable {
                        onEventSent(RescuedAnimalContract.Event.OnFilterClicked(filter = uiState.value.filterState))
                    }, vector = Icons.Default.FilterAlt, tint = Color.White
                )
            }
            HDivider(modifier = Modifier.padding(horizontal = 20.dp))
            CustomPullToRefreshBox(modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp),
                onRefresh = { onEventSent(RescuedAnimalContract.Event.LoadMore(refresh = true)) }) {
                AnimalList(modifier = Modifier.fillMaxSize(),
                    listState = listState,
                    itemList = uiState.value.rescuedAnimalListState,
                    onLoadMore = { refresh ->
                        onEventSent(RescuedAnimalContract.Event.LoadMore(refresh = refresh))
//                        coroutineScope.launch {
//                            rescuedAnimalViewModel.getRescuedAnimal(
//                                refresh = refresh
//                            )
//                        }
                    },
                    itemClicked = { index, animal ->
                        onEventSent(RescuedAnimalContract.Event.OnListItemClicked(animal))
//                            coroutineScope.launch {
//                                if (animal.favorite == true)
//                                    rescuedAnimalViewModel.deleteFavoriteAnimal(
//                                        index = index,
//                                        animal = animal
//                                    )
//                                else {
//                                    rescuedAnimalViewModel.insertFavoriteAnimal(
//                                        index = index,
//                                        animal = animal
//                                    )
//
//                                }
//                            }
                    },
                    favoriteClicked = { index, animal ->
                        onEventSent(
                            RescuedAnimalContract.Event.OnItemFavoriteClicked(
                                index, animal
                            )
                        )
                    })
            }
        }
//        else
//            Row {
//                Header(
//                    route = Screen.RescuedAnimalScreen,
//                    rightButtonClicked = {
//                        navController.navigate(Screen.FavoriteScreen.route)
//                    })
//                VDivider(modifier = Modifier.padding(vertical = 20.dp))
//                CustomPullToRefreshBox(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 20.dp),
//                    onRefresh = { rescuedAnimalViewModel.getRescuedAnimal(refresh = true) }) {
//                    AnimalList(
//                        modifier = Modifier.fillMaxSize(),
//                        listState = listState,
//                        itemListState = rescuedAnimalViewModel.rescuedAnimalList,
//                        onLoadMore = { refresh ->
//                            coroutineScope.launch {
//                                rescuedAnimalViewModel.getRescuedAnimal(
//                                    refresh = refresh
//                                )
//                            }
//                        },
//                        itemClicked = { index, animal ->
//                            coroutineScope.launch {
//                                if (animal.favorite == true)
//                                    rescuedAnimalViewModel.deleteFavoriteAnimal(
//                                        index = index,
//                                        animal = animal
//                                    )
//                                else {
//                                    rescuedAnimalViewModel.insertFavoriteAnimal(
//                                        index = index,
//                                        animal = animal
//                                    )
//
//                                }
//                            }
//                        }
//                    )
//                }
//            }
    }
}