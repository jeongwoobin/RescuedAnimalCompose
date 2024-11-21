package com.example.presentation.screens.home

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
import com.example.presentation.navigation.MyBottomNavigation
import com.example.presentation.navigation.graph.HomeNavGraph
import com.example.presentation.screens.SetNavigation

@Composable
fun HomeScreen(navController: NavHostController) {

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        MyBottomNavigation(
            modifier = Modifier.height(56.dp),
            containerColor = Color.Green,
            contentColor = Color.White,
            indicatorColor = Color.Green,
            navController = navController
        )
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            HomeNavGraph(navController = navController)
        }
    }
}