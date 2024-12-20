package com.example.presentation.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.presentation.component.DefaultSnackBar

@Composable
fun BaseScreen(
    snackbarHostState: SnackbarHostState,
    loadingState: Boolean,
    loadingProgressBar: @Composable () -> Unit = {},
    fab: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
//    val loading by loadingStateFlow.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = { fab() }) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                content()
            }

            if (loadingState) {
                loadingProgressBar()
            }

            DefaultSnackBar(
                snackBarHostState = snackbarHostState,
                modifier = Modifier.align(alignment = Alignment.BottomCenter)
            )
        }
    }
}