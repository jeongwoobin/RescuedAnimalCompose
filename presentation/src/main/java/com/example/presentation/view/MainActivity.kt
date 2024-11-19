package com.example.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.example.presentation.ui.theme.RescuedAnimalsTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import com.example.presentation.navigation.Screen
import com.example.presentation.navigation.MainNavGraph
import com.example.presentation.navigation.MyBottomNavigation
import com.example.presentation.ui.theme.Primary_Red_300

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            RescuedAnimalsTheme {
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
                        SetNavigation(navController = navController, internetConnected = true)
                    }
                }
            }
        }
    }
}

@Composable
fun SetNavigation(navController: NavHostController, internetConnected: Boolean) {
    val startDestination =
//        if (internetConnected)
        Screen.RescuedAnimalScreen.route
//        else Screen.FavoriteScreen.route
    MainNavGraph(navController = navController, startDestination = startDestination)
}