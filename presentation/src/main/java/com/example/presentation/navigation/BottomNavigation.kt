package com.example.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import com.example.presentation.R
import com.example.presentation.navigation.graph.HomeGraph
import com.orhanobut.logger.Logger
import com.skydoves.landscapist.glide.GlideImage

sealed class BottomNavItem(
    // Resource ID of the title for the menu item
    @StringRes val title: Int,
    // Resource ID of the icon for the menu item
    @DrawableRes val icon: Int,
    // Route of a destination to navigate
    val screenRoute: HomeGraph
) {

    data object Home : BottomNavItem(
        R.string.home, R.drawable.ic_home, HomeGraph.RescuedAnimal
    )

    data object Favorites : BottomNavItem(
        R.string.favorites, R.drawable.ic_favorite, HomeGraph.Favorite
    )

    data object MyPage : BottomNavItem(
        R.string.myPage, R.drawable.ic_mypage, HomeGraph.MyPage
    )
}

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
        BottomNavItem.Home, BottomNavItem.Favorites, BottomNavItem.MyPage
    )

    Logger.d("currentRoute: $currentRoute")

    AnimatedVisibility(
        visible = true //items.map { it.screenRoute }.contains(currentRoute)
    ) {
        NavigationBar(
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor,
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute?.hierarchy?.any { it ->
                        it.hasRoute(item::class)
                    } == true,//currentRoute == item.screenRoute,
                    label = {
                        Text(
                            text = stringResource(id = item.title),
//                            style = TextStyle(
//                                fontSize = 12.sp
//                            )
                        )
                    },
                    icon = {
                        GlideImage(modifier = Modifier.size(24.dp), imageModel = { item.icon })
                    },
                    onClick = {
                        navController.navigate(item.screenRoute) {

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