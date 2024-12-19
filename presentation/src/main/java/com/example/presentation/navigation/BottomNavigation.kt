package com.example.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.presentation.component.HDivider
import com.example.presentation.navigation.graph.FavoriteGraph
import com.example.presentation.navigation.graph.BottombarGraph
import com.example.presentation.navigation.graph.MyPageGraph
import com.example.presentation.navigation.graph.RescuedAnimalGraph
import com.example.presentation.ui.theme.Text_400
import com.orhanobut.logger.Logger

@Composable
fun MyBottomNavigation(
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    indicatorColor: Color,
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val items = listOf(
        BottombarGraph.RescuedAnimal, BottombarGraph.Favorite, /*BottombarGraph.MyPage*/
    )

    Logger.d("currentRoute: $currentRoute")

    AnimatedVisibility(visible = currentRoute?.let {
        it.hasRoute(RescuedAnimalGraph.RescuedAnimal::class) || it.hasRoute(FavoriteGraph.Favorite::class) || it.hasRoute(
            MyPageGraph.MyPage::class
        )
    } ?: false) {
        Column {
            HDivider(height = 0.3.dp)
            NavigationBar(
                modifier = modifier,
                containerColor = containerColor,
                contentColor = contentColor,
            ) {
                items.forEach { item ->
                    val selected = currentRoute?.hierarchy?.any { it ->
                        it.hasRoute(item::class)
                    } == true
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(indicatorColor = indicatorColor),
                        selected = selected,
                        label = {
                            Text(
                                text = stringResource(id = item.title),
                                style = LocalTextStyle.current.copy(color = if (selected) Color.White else Text_400)
                            )
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = item.icon),
                                contentDescription = null,
                                tint = if (selected) Color.White else Text_400
                            )
                        },
                        onClick = {
                            navController.navigate(item) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        }
    }
}