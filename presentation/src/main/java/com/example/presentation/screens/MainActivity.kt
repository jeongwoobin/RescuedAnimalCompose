package com.example.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import com.example.presentation.ui.theme.RescuedAnimalsTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import com.example.domain.entity.Sido
import com.example.presentation.component.LoadingProgressBar
import com.example.presentation.navigation.graph.RootNavGraph
import com.example.presentation.navigation.graph.RootGraph
import com.example.presentation.ui.theme.Primary_Blue_400
import com.example.presentation.ui.theme.Primary_Red_400

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    companion object {
        var sido by mutableStateOf<List<Sido?>?>(listOf(null))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        viewModel.getSido()

        setContent {
            RescuedAnimalsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
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