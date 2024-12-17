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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.domain.entity.Neuter
import com.example.domain.entity.Shelter
import com.example.domain.entity.Sido
import com.example.domain.entity.Sigungu
import com.example.domain.entity.Upkind
import com.example.presentation.base.BaseScreen
import com.example.presentation.component.CustomDatePickerDialog
import com.example.presentation.component.CustomRadioBtn
import com.example.presentation.component.Header
import com.example.presentation.component.LoadingProgressBar
import com.example.presentation.screens.MainActivity
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
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    AboutSido(filter = filter, onValueChanged = { sido ->
                        onEventSent(
                            SearchFilterContract.Event.OnSidoClicked(sido = sido)
                        )
                    })
                    Spacer(modifier = Modifier.height(20.dp))
                }
                filter.upr_cd?.let {
                    item {
                        AboutSigungu(filter = filter, onValueChanged = { sigungu ->
                            onEventSent(
                                SearchFilterContract.Event.OnSigunguClicked(sigungu = sigungu)
                            )
                        })
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
                filter.org_cd?.let {
                    item {
                        AboutShelter(filter = filter, onValueChanged = { shelter ->
                            onEventSent(
                                SearchFilterContract.Event.OnShelterClicked(shelter = shelter)
                            )
                        })
                        Spacer(modifier = Modifier.height(20.dp))
                    }
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
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    AboutUpkind(filter = filter, onValueChanged = { upkind ->
                        onEventSent(
                            SearchFilterContract.Event.OnUpkindClicked(upkind = upkind)
                        )
                    })
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    AboutNeuter(filter = filter, onValueChanged = { neuter ->
                        onEventSent(
                            SearchFilterContract.Event.OnNeuterClicked(neuter = neuter)
                        )
                    })
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    AboutState(filter = filter, onValueChanged = { state ->
                        onEventSent(
                            SearchFilterContract.Event.OnStateClicked(state = state)
                        )
                    })
                    Spacer(modifier = Modifier.height(20.dp))
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
private fun AboutSido(
    filter: AnimalSearchFilter, onValueChanged: (sido: Sido?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Text(text = "시도 선택")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp, color = Color.White, shape = RoundedCornerShape(6.dp)
            )
            .padding(4.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            text = filter.upr_cd?.orgdownNm ?: "전체"
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            MainActivity.sido?.forEach { sido ->
                DropdownMenuItem(text = { Text(text = sido?.orgdownNm ?: "전체") },
                    onClick = { onValueChanged(sido) })
            }
        }
    }
}

@Composable
private fun AboutSigungu(
    filter: AnimalSearchFilter, onValueChanged: (sigungu: Sigungu?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Text(text = "시군구 선택")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp, color = Color.White, shape = RoundedCornerShape(6.dp)
            )
            .padding(4.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            text = filter.org_cd?.orgdownNm ?: "전체"
        )
//        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
//            MainActivity.sido?.forEach { sido ->
//                DropdownMenuItem(text = { Text(text = sido?.orgdownNm ?: "전체") },
//                    onClick = { onValueChanged(sido) })
//            }
//        }
    }
}

@Composable
private fun AboutShelter(
    filter: AnimalSearchFilter, onValueChanged: (shelter: Shelter?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Text(text = "보호소 선택")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp, color = Color.White, shape = RoundedCornerShape(6.dp)
            )
            .padding(4.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            text = filter.care_reg_no?.careNm ?: "전체"
        )
//        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
//            MainActivity.sido?.forEach { sido ->
//                DropdownMenuItem(text = { Text(text = sido?.orgdownNm ?: "전체") },
//                    onClick = { onValueChanged(sido) })
//            }
//        }
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
                Text(text = upkind.kor)
            }
        }
    }
}

@Composable
private fun AboutNeuter(filter: AnimalSearchFilter, onValueChanged: (Neuter) -> Unit) {
    Text(text = "중성화 여부")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Neuter.entries.forEach { neuter ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                CustomRadioBtn(isSelected = filter.neuter == neuter,
                    onValueChanged = { onValueChanged(neuter) })
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = neuter.kor)
            }
        }
    }

}


@Composable
private fun AboutState(
    filter: AnimalSearchFilter, onValueChanged: (com.example.domain.entity.State) -> Unit
) {
    Text(text = "상태")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        com.example.domain.entity.State.entries.forEach { state ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                CustomRadioBtn(
                    isSelected = filter.state == state,
                    onValueChanged = { onValueChanged(state) })
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = state.kor)
            }
        }
    }

}