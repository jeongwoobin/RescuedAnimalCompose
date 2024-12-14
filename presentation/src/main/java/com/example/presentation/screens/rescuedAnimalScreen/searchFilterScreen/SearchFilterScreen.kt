package com.example.presentation.screens.rescuedAnimalScreen.searchFilterScreen

//import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.domain.entity.AnimalSearchFilter
import com.example.domain.entity.Upkind
import com.example.presentation.base.BaseScreen
import com.example.presentation.component.CustomDatePickerDialog
import com.example.presentation.component.CustomRadioBtn
import com.example.presentation.component.Header
import com.example.presentation.component.LoadingProgressBar
import com.example.presentation.ui.theme.Text_600
import com.example.presentation.utils.Utils
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import java.util.TimeZone

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
                    Spacer(modifier = Modifier.height(15.dp))
                }
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AboutDate(modifier = Modifier.weight(1f),
                            title = "검색 시작일",
                            date = filter.bgnde,
                            isSelectableDate = { utcTimeMillis ->
                                val selectableDate = filter.endde?.let {
                                    (Utils.dateFormat.apply {
                                        timeZone = TimeZone.getTimeZone("UTC")
                                    }.parse(it)?.time
                                        ?: System.currentTimeMillis()) >= utcTimeMillis
                                } ?: true
                                selectableDate && utcTimeMillis < System.currentTimeMillis()
                            },
                            OnDateInitClicked = {
                                onEventSent(
                                    SearchFilterContract.Event.OnDateInitClicked
                                )
                            },
                            onValueChanged = { date ->
                                onEventSent(
                                    SearchFilterContract.Event.OnDateChanged(
                                        isStartDate = true, date = date
                                    )
                                )
                            })
                        Spacer(modifier = Modifier.width(10.dp))
                        AboutDate(modifier = Modifier.weight(1f),
                            title = "검색 종료일",
                            date = filter.endde,
                            isSelectableDate = { utcTimeMillis ->
                                val selectableDate = filter.bgnde?.let {
                                    (Utils.dateFormat.apply {
                                        timeZone = TimeZone.getTimeZone("UTC")
                                    }.parse(it)?.time
                                        ?: System.currentTimeMillis()) <= utcTimeMillis
                                } ?: true
                                selectableDate && utcTimeMillis < System.currentTimeMillis()
                            },
                            OnDateInitClicked = {
                                onEventSent(
                                    SearchFilterContract.Event.OnDateInitClicked
                                )
                            },
                            onValueChanged = { date ->
                                onEventSent(
                                    SearchFilterContract.Event.OnDateChanged(
                                        isStartDate = false, date = date
                                    )
                                )
                            })
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
                item {
                    AboutUpkind(filter = filter, onValueChanged = { upkind ->
                        onEventSent(
                            SearchFilterContract.Event.OnUpkindClicked(upkind = upkind)
                        )
                    })
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(5.dp))
                    .clickable {
                        popBackStack(uiState.value.filterState)
                    }, contentAlignment = Alignment.Center
            ) {
                Text(
                    "저장하기", style = TextStyle(
                        color = Text_600, fontSize = 14.sp, fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

@Composable
private fun AboutDate(
    modifier: Modifier,
    title: String?,
    date: String?,
    isSelectableDate: (utcTimeMillis: Long) -> Boolean,
    OnDateInitClicked: () -> Unit,
    onValueChanged: (date: String?) -> Unit
) {
    var isDialogShow by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(text = title.toString())
        Box(modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp, color = Color.White, shape = RoundedCornerShape(6.dp)
            )
            .clickable {
                isDialogShow = true
            }
            .padding(4.dp)) {
            Text(date ?: "전체 기간")
        }
    }

    if (isDialogShow) {
        CustomDatePickerDialog(title = title,
            selectedDate = date,
            isSelectable = isSelectableDate,
            onClickCancel = { isDialogShow = false },
            OnDateInitClicked = {
                OnDateInitClicked()
                isDialogShow = false
            },
            onClickConfirm = { result ->
                Logger.d("onClickConfirm: $result")
                onValueChanged(result)
                isDialogShow = false
            })
    }
}

@Composable
private fun AboutUpkind(filter: AnimalSearchFilter, onValueChanged: (Upkind) -> Unit) {
    Text(text = "축종")
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