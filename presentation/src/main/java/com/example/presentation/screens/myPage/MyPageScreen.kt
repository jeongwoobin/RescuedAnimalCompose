package com.example.presentation.screens.myPage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.presentation.base.BaseScreen
import com.example.presentation.component.LinearProgressBar

@Composable
fun MyPageScreen(
    navController: NavController,
    rescuedAnimalViewModel: MyPageViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyGridState()
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbar by rescuedAnimalViewModel.snackbarEvent.collectAsStateWithLifecycle()

    BaseScreen(
        snackbarHostState = snackbarHostState,
        loadingState = false,
        loadingProgressBar = { LinearProgressBar() },
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(56.dp).clickable {  })
            Text(text = "MyPage")
        }
    }
}