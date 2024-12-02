package com.example.presentation.navigation.graph

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.domain.entity.Animal
import com.example.presentation.R
import com.example.presentation.utils.serializableType
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

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

sealed interface RescuedAnimalGraph {
    @Serializable
    data object RescuedAnimal : RescuedAnimalGraph {

        @Serializable
        data class RescuedAnimalDetail(val animal: Animal) : RescuedAnimalGraph {
            companion object {
                val typeMap = mapOf(
                    typeOf<Animal>() to serializableType<Animal>(),
                )
            }
        }
    }
}

sealed interface FavoriteGraph {
    @Serializable
    data object Favorite : FavoriteGraph{

        @Serializable
        data class FavoriteDetail(val animal: Animal) : FavoriteGraph {
            companion object {
                val typeMap = mapOf(
                    typeOf<Animal>() to serializableType<Animal>(),
                )
            }
        }
    }
}

sealed interface MyPageGraph {
    @Serializable
    data object MyPage : MyPageGraph
}