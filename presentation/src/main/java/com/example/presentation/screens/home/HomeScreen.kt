package com.example.presentation.screens.home

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.presentation.navigation.MyBottomNavigation
import com.example.presentation.navigation.graph.HomeNavGraph
import com.example.presentation.screens.MainActivity
import com.example.presentation.screens.SetNavigation
import com.example.presentation.ui.theme.Primary_Blue_400
import com.example.presentation.ui.theme.Primary_Green_400
import com.example.presentation.ui.theme.Primary_Red_400
import com.example.presentation.ui.theme.Text_400
import com.example.presentation.ui.theme.Text_600
import com.orhanobut.logger.Logger

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    var waitTime by remember { mutableStateOf(0L) }

    BackHandler {
        val current = System.currentTimeMillis()
        if (current - waitTime >= 1500) {
            waitTime = current
            Toast.makeText(context, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            if (context is MainActivity) {
                context.finishAffinity()
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        MyBottomNavigation(
            modifier = Modifier.height(56.dp),
            containerColor = Text_600,
            contentColor = Color.White,
            indicatorColor = Text_600,
            navController = navController
        )
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HomeNavGraph(navController = navController)
        }
    }
}