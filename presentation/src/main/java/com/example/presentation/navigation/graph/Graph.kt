package com.example.presentation.navigation.graph

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.domain.entity.Animal
import com.example.domain.entity.AnimalSearchFilter
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
sealed class BottombarGraph(
    // Resource ID of the title for the menu item
    @StringRes val title: Int,
    // Resource ID of the icon for the menu item
    @DrawableRes val icon: Int,
    // Route of a destination to navigate
//    val screenRoute: HomeGraph
) {
    @Serializable
    data object RescuedAnimal : BottombarGraph(
        R.string.home, R.drawable.ic_home,
    )

    @Serializable
    data object Favorite : BottombarGraph(
        R.string.favorite, R.drawable.ic_favorite,
    )

    @Serializable
    data object MyPage : BottombarGraph(
        R.string.myPage, R.drawable.ic_mypage,
    )
}

sealed interface RescuedAnimalGraph {
    @Serializable
    data object RescuedAnimal : RescuedAnimalGraph {
        @Serializable
        data class SearchFilter(val filter: AnimalSearchFilter) {
            companion object {
                val typeMap = mapOf(
                    typeOf<AnimalSearchFilter>() to serializableType<AnimalSearchFilter>(),
                )
            }
        }
    }
}

sealed interface FavoriteGraph {
    @Serializable
    data object Favorite : FavoriteGraph {}
}

sealed interface MyPageGraph {
    @Serializable
    data object MyPage : MyPageGraph
}

sealed interface DetailGraph {
    @Serializable
    data class Detail(val animal: Animal) {
        companion object {
            val typeMap = mapOf(
                typeOf<Animal>() to serializableType<Animal>(),
            )
        }
    }
}