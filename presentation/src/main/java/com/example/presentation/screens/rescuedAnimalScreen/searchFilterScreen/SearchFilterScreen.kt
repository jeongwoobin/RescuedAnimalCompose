package com.example.presentation.screens.rescuedAnimalScreen.searchFilterScreen

//import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.domain.entity.AnimalSearchFilter
import com.example.domain.entity.Upkind
import com.example.presentation.base.BaseScreen
import com.example.presentation.component.CustomDatePickerDialog
import com.example.presentation.component.CustomRadioBtn
import com.example.presentation.component.Header
import com.example.presentation.component.LoadingProgressBar
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchFilterScreen(
    navController: NavController,
    uiState: State<SearchFilterContract.State>,
    onEventSent: (event: SearchFilterContract.Event) -> Unit,
    effectFlow: Flow<SearchFilterContract.Effect>,
    onNavigationRequested: (navigationEffect: SearchFilterContract.Effect.Navigation) -> Unit,
    popBackStack: (AnimalSearchFilter?) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyGridState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(effectFlow) {
        effectFlow.collectLatest { effect ->
            Logger.d("effectFlow: $effect")
            when (effect) {
                is SearchFilterContract.Effect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.content, null)
                }

                is SearchFilterContract.Effect.Navigation -> onNavigationRequested(effect)
            }
        }
    }

    BaseScreen(
        snackbarHostState = snackbarHostState,
        loadingState = false,
        loadingProgressBar = { LoadingProgressBar() },
    ) {
        Header(title = "검색 필터", backButtonClicked = { popBackStack(null) })
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .weight(1f)
        ) {
            uiState.value.filterState.let { filter ->
                item {
                    Text(text = "filter: $filter")
                }
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AboutDate(modifier = Modifier.weight(1f),
                            date = filter.bgnde,
                            onValueChanged = { date ->
                                onEventSent(
                                    SearchFilterContract.Event.OnDateChanged(
                                        isStartDate = true, date = date
                                    )
                                )
                            })
                        Spacer(modifier = Modifier.width(10.dp))
                        AboutDate(modifier = Modifier.weight(1f),
                            date = filter.endde,
                            onValueChanged = { date ->
                                onEventSent(
                                    SearchFilterContract.Event.OnDateChanged(
                                        isStartDate = false, date = date
                                    )
                                )
                            })
                    }
                }
                item {
                    AboutUpkind(filter = filter, onValueChanged = { upkind ->
                        onEventSent(
                            SearchFilterContract.Event.OnUpkindClicked(upkind = upkind)
                        )
                    })
                }
            }
        }
        Box(modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(color = Color.White)
            .clickable {
                popBackStack(uiState.value.filterState)
            }) {
            Text("Save")
        }
    }
}

@Composable
private fun AboutDate(modifier: Modifier, date: String?, onValueChanged: (date: String) -> Unit) {
    var isDialogShow by remember { mutableStateOf(false) }

    Box(modifier = modifier
        .border(
            width = 1.dp, color = Color.White, shape = RoundedCornerShape(6.dp)
        )
        .clickable {
            isDialogShow = true
        }) {
        Text(date.toString())
    }

    if (isDialogShow) {
        CustomDatePickerDialog(selectedDate = date,
            onClickCancel = { isDialogShow = false },
            onClickConfirm = { result ->
                Logger.d("onClickConfirm: $result")
                onValueChanged(result)
                isDialogShow = false
            })
    }
}

@Composable
private fun AboutUpkind(filter: AnimalSearchFilter, onValueChanged: (Upkind) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Upkind.entries.forEach { upkind ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                CustomRadioBtn(isSelected = filter.upkind == upkind,
                    onValueChanged = { onValueChanged(upkind) })
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = upkind.name)
            }
        }
    }
}