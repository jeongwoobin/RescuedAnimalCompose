package com.example.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.presentation.navigation.MyBottomNavigation
import com.example.presentation.navigation.graph.HomeNavGraph
import com.example.presentation.screens.SetNavigation
import com.example.presentation.ui.theme.Text_400
import com.example.presentation.ui.theme.Text_600

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {

    Scaffold(modifier = Modifier.fillMaxSize().background(color = Text_600), bottomBar = {
        MyBottomNavigation(
            modifier = Modifier.height(56.dp),
            containerColor = Text_600,
            contentColor = Color.White,
            indicatorColor = Text_600,
            navController = navController
        )
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            HomeNavGraph(navController = navController)
        }
    }
}