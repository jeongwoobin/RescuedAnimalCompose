package com.example.presentation.screens.animalDetailScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
//import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.toRoute
import com.example.domain.entity.Animal
import com.example.presentation.base.BaseScreen
import com.example.presentation.component.Header
import com.example.presentation.component.LinearProgressBar
import com.example.presentation.component.LoadingIcon
import com.example.presentation.component.PlaceHolderIcon
import com.example.presentation.component.VectorIcon
import com.example.presentation.navigation.graph.RescuedAnimalGraph
import com.example.presentation.screens.favoriteScreen.FavoriteContract
import com.example.presentation.ui.theme.Primary_Red_500
import com.orhanobut.logger.Logger
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimalDetailScreen(
    navController: NavController,
    uiState: State<AnimalDetailContract.State>,
    onEventSent: (event: AnimalDetailContract.Event) -> Unit,
    effectFlow: Flow<AnimalDetailContract.Effect>,
    onNavigationRequested: (navigationEffect: AnimalDetailContract.Effect.Navigation) -> Unit,
    popBackStack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyGridState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(effectFlow) {
        effectFlow.collectLatest { effect ->
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
        LazyColumn {
            stickyHeader {
                Header(backButtonClicked = popBackStack)
            }
            uiState.value.animalState.let { animal ->
                item {
                    AnimalImage(animal = animal)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                        VectorIcon(
                            modifier = Modifier.clickable {
                                onEventSent (AnimalDetailContract.Event.OnItemFavoriteClicked(animal = animal))
                            },
                            vector = if (animal.favorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            tint = Primary_Red_500
                        )
                    }
                    // 고유번호, 등록날짜, 발견장소,
                    // 품종, 색상, 나이, 체중, 성별, 중성화여부, 특징
                    // 공고번호, 공고시작일 ~ 공고종료일, 상태
                    // 보호소 이름(주소(네이버지도 연동?)), 보호소 전화번호
                    // 관할기관, 담당자, 연락처
                    Text(text = "Detail Screen id: ${animal.desertionNo}")
                }
            }
        }
    }
}

@Composable
private fun AnimalImage(animal: Animal) {
    GlideImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        imageModel = { animal.popfile }, // loading a network image using an URL.
        imageOptions = ImageOptions(
            contentScale = ContentScale.Fit, alignment = Alignment.Center
        ), loading = { LoadingIcon() }, failure = {
            PlaceHolderIcon()
        })
}