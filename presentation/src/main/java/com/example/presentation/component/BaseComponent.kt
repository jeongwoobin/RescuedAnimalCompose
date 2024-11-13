package com.example.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Http
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.VerticalDivider
//import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
//import androidx.window.core.layout.WindowWidthSizeClass
import com.example.presentation.navigation.Screen
import com.example.presentation.ui.theme.Line_Thin
import com.example.presentation.ui.theme.Primary_Red_500
import com.example.presentation.ui.theme.Text_600
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun Header(
    backButtonClicked: (() -> Unit)? = null,
    route: Screen,
    rightButtonClicked: (() -> Unit)? = null
) {
//    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

//    if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            backButtonClicked?.let { onClicked ->
                VectorIcon(
                    modifier = Modifier.clickable {
                        onClicked()
                    },
                    vector = Icons.Default.ArrowBackIosNew,
                    tint = Text_600,
                    contentDescription = "go to back"
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            rightButtonClicked?.let { onClicked ->
                VectorIcon(
                    modifier = Modifier.clickable {
                        onClicked()
                    },
                    vector = if (route == Screen.RescuedAnimalScreen) Icons.Default.Favorite else Icons.Default.FormatListNumbered,
                    tint = if (route == Screen.RescuedAnimalScreen) Primary_Red_500 else Text_600,
                    contentDescription = "right button icon"
                )
            }
        }
//    else
//        Column(
//            modifier = Modifier
//                .fillMaxHeight()
//                .padding(vertical = 20.dp, horizontal = 10.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            backButtonClicked?.let { onClicked ->
//                VectorIcon(
//                    modifier = Modifier.clickable {
//                        onClicked()
//                    },
//                    vector = Icons.Default.ArrowBackIosNew,
//                    tint = Text_600,
//                    contentDescription = "go to back"
//                )
//            }
//            Spacer(modifier = Modifier.weight(1f))
//            rightButtonClicked?.let { onClicked ->
//                VectorIcon(
//                    modifier = Modifier.clickable {
//                        onClicked()
//                    },
//                    vector = if (route == Screen.RescuedAnimalScreen) Icons.Default.Favorite else Icons.Default.FormatListNumbered,
//                    tint = if (route == Screen.RescuedAnimalScreen) Primary_Red_500 else Text_600,
//                    contentDescription = "right button icon"
//                )
//            }
//        }
}

@Composable
fun GoToTopFAB(onClicked: () -> Unit) {
    FloatingActionButton(onClick = onClicked) {
        VectorIcon(
            vector = Icons.Filled.ArrowCircleUp,
            tint = Color.Black,
            contentDescription = "go to first item"
        )
    }
}

@Composable
fun HDivider(modifier: Modifier = Modifier, color: Color = Line_Thin) {
    HorizontalDivider(
        modifier = modifier
            .height(1.dp)
            .fillMaxWidth(), color = color
    )
}

@Composable
fun VDivider(modifier: Modifier = Modifier, color: Color = Line_Thin) {
    VerticalDivider(
        modifier = modifier
            .width(1.dp)
            .fillMaxHeight(), color = color
    )
}

@Composable
fun VectorIcon(
    modifier: Modifier = Modifier,
    vector: ImageVector,
    tint: Color = LocalContentColor.current,
    contentDescription: String? = null
) {
    Icon(
        modifier = modifier,
        painter = rememberVectorPainter(image = vector),
        tint = tint,
        contentDescription = contentDescription
    )
}

@Composable
fun LoadingIcon() {
    VectorIcon(
        vector = Icons.Default.Downloading,
        contentDescription = "image loading"
    )
}

@Composable
fun PlaceHolderIcon() {
    VectorIcon(
        vector = Icons.Default.Http,
        contentDescription = "image placeholder"
    )
}

@Composable
fun LinearProgressBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPullToRefreshBox(
    modifier: Modifier = Modifier, onRefresh: suspend () -> Unit,
    content: @Composable() () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox(
        modifier = modifier,
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            coroutineScope.launch {
                onRefresh()
                delay(1000)
                isRefreshing = false
            }
        },
        indicator = {
            PullToRefreshDefaults.Indicator(
                state = pullToRefreshState,
                isRefreshing = isRefreshing,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }) { content() }
}