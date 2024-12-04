package com.example.presentation.screens.animalDetailScreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
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
import com.example.presentation.ui.theme.Primary_Blue_600
import com.example.presentation.ui.theme.Primary_Red_500
import com.example.presentation.utils.Utils
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
        Header(backButtonClicked = popBackStack)
        LazyColumn(modifier = Modifier.padding(horizontal = 20.dp)) {
            uiState.value.animalState.let { animal ->
                item {
                    AnimalImage(animal = animal)
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        VectorIcon(
                            modifier = Modifier.clickable {
                                onEventSent(AnimalDetailContract.Event.OnItemFavoriteClicked(animal = animal))
                            },
                            vector = if (animal.favorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            tint = Primary_Red_500
                        )
                    }
                }
                item {
                    AboutRescued(animal = animal)
                }
                item {
                    AboutAnimal(animal = animal)
                }
                item {
                    AboutNotice(animal = animal)
                }
                item {
                    AboutCareCenter(animal = animal)
                }
                item {
                    AboutOffice(animal = animal)
                }
            }
        }
    }
}

@Composable
private fun AnimalImage(animal: Animal) {
    GlideImage(modifier = Modifier
        .fillMaxWidth()
        .height(250.dp),
        imageModel = { animal.popfile }, // loading a network image using an URL.
        imageOptions = ImageOptions(
            contentScale = ContentScale.Fit, alignment = Alignment.Center
        ),
        loading = { LoadingIcon() },
        failure = {
            PlaceHolderIcon()
        })
}

@Composable
private fun TitleAndContentRow(
    title: String,
    content: String? = null,
    composableContent: (@Composable RowScope.() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Row(
            modifier = Modifier.weight(0.25f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (t in title) {
                Text(text = t.toString())
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        if (composableContent != null) {
            composableContent()
        } else {
            TitleAndContentRowContent(content = content)
        }
    }
}

@Composable
private fun RowScope.TitleAndContentRowContent(
    modifier: Modifier = Modifier, content: String?, style: TextStyle? = null
) {
    Text(
        modifier = modifier.weight(1f),
        text = content ?: "-",
        style = style ?: LocalTextStyle.current
    )
}

// 고유번호, 등록날짜, 발견장소,
@Composable
private fun AboutRescued(animal: Animal) {
//    TitleAndContentRow(title = "고유번호", content = animal.desertionNo.toString())
    TitleAndContentRow(title = "등록날짜", content = Utils.convertMyDateFormat(animal.happenDt))
    TitleAndContentRow(title = "발견장소", content = animal.happenPlace)
    Spacer(modifier = Modifier.height(10.dp))
}

// 품종, 색상, 나이, 체중, 성별, 중성화여부, 특징
@Composable
private fun AboutAnimal(animal: Animal) {
    TitleAndContentRow(title = "품종", content = animal.kindCd)
    TitleAndContentRow(title = "색상", content = animal.colorCd)
    TitleAndContentRow(title = "나이", content = animal.age)
    TitleAndContentRow(title = "체중", content = animal.weight)
    TitleAndContentRow(title = "성별", content = animal.sexCd)
    TitleAndContentRow(title = "중성화여부", content = animal.neuterYn)
    TitleAndContentRow(title = "특징", content = animal.specialMark)
    Spacer(modifier = Modifier.height(10.dp))
}

// 공고번호, 공고시작일 ~ 공고종료일, 상태
@Composable
private fun AboutNotice(animal: Animal) {
    TitleAndContentRow(title = "공고번호", content = animal.noticeNo)
    TitleAndContentRow(
        title = "공고일", content = "${Utils.convertMyDateFormat(animal.noticeSdt)} - ${
            Utils.convertMyDateFormat(animal.noticeEdt)
        }"
    )
    TitleAndContentRow(title = "상태", content = animal.processState)
    Spacer(modifier = Modifier.height(10.dp))
}

// 보호소 이름(주소(네이버지도 연동?)), 보호소 전화번호
@Composable
private fun AboutCareCenter(animal: Animal) {
    val context = LocalContext.current
    TitleAndContentRow(title = "보호소") {
        TitleAndContentRowContent(
            modifier = Modifier.clickable {
                animal.careAddr?.let { location ->
                    Utils.intentActionView(context = context, location = location)
                }
            },
            content = "${animal.careNm}\n(${animal.careAddr})",
            style = LocalTextStyle.current.copy(color = Primary_Blue_600)
        )
    }
    TitleAndContentRow(title = "전화번호") {
        TitleAndContentRowContent(modifier = Modifier.clickable {
            animal.careTel?.let { tel ->
                Utils.intentActionDial(context = context, tel = tel)
            }
        }, content = animal.careTel, style = LocalTextStyle.current.copy(color = Primary_Blue_600))
    }
    Spacer(modifier = Modifier.height(10.dp))
}

// 관할기관, 담당자, 연락처
@Composable
private fun AboutOffice(animal: Animal) {
    val context = LocalContext.current

    TitleAndContentRow(title = "관할기관", content = animal.orgNm)
    TitleAndContentRow(title = "담당자", content = animal.chargeNm)
    TitleAndContentRow(title = "연락처") {
        TitleAndContentRowContent(
            modifier = Modifier.clickable {
                animal.officetel?.let { tel ->
                    Utils.intentActionDial(context = context, tel = tel)
                }
            },
            content = animal.officetel,
            style = LocalTextStyle.current.copy(color = Primary_Blue_600)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}