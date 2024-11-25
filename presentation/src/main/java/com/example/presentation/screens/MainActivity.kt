package com.example.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.presentation.ui.theme.RescuedAnimalsTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import com.example.presentation.navigation.graph.RootNavGraph
import com.example.presentation.navigation.graph.RootGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            RescuedAnimalsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    Box(modifier = Modifier.padding(innerPadding)) {
                        SetNavigation(navController = navController, internetConnected = true)
                    }
                }
            }
        }
    }
}

@Composable
fun SetNavigation(navController: NavHostController, internetConnected: Boolean) {
    val startDestination = RootGraph.Home
    RootNavGraph(navController = navController, startDestination = startDestination)
}