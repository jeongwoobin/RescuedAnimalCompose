package com.example.presentation.navigation.graph

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.presentation.R
import kotlinx.serialization.Serializable

sealed interface RootGraph {
    @Serializable
    data object Root : RootGraph

    @Serializable
    data object Auth : RootGraph

    @Serializable
    data object Home : RootGraph
}

sealed interface AuthGraph {
    @Serializable
    data object SignIn : AuthGraph
}

@Serializable
sealed class HomeGraph(
    // Resource ID of the title for the menu item
    @StringRes val title: Int,
    // Resource ID of the icon for the menu item
    @DrawableRes val icon: Int,
    // Route of a destination to navigate
//    val screenRoute: HomeGraph
) {
    @Serializable
    data object RescuedAnimal : HomeGraph(
        R.string.home, R.drawable.ic_home,
    )

    @Serializable
    data object Favorite : HomeGraph(
        R.string.favorite, R.drawable.ic_favorite,
    )

    @Serializable
    data object MyPage : HomeGraph(
        R.string.myPage, R.drawable.ic_mypage,
    )
}

//fun NavDestination.bottomNavVisibility(): Boolean {
//    return hierarchy.any { it ->
//        it.hasRoute(RescuedAnimalGraph.RescuedAnimal::class)
//    }
//}

sealed interface RescuedAnimalGraph {
    @Serializable
    data object RescuedAnimal : RescuedAnimalGraph {

        @Serializable
        data class RescuedAnimalDetail(val id: String) : RescuedAnimalGraph
    }

//    @Serializable
//    data class RescuedAnimalDetail2(val id: String) : RescuedAnimalGraph
}


sealed interface FavoriteGraph {
    @Serializable
    data object Favorite : FavoriteGraph
}


sealed interface MyPageGraph {
    @Serializable
    data object MyPage : MyPageGraph
}