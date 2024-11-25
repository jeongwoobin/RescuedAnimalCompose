package com.example.presentation.navigation.graph

import kotlinx.serialization.Serializable

sealed interface RootGraph {
    @Serializable
    data object Root: RootGraph
    @Serializable
    data object Auth: RootGraph
    @Serializable
    data object Home: RootGraph
}

sealed interface AuthGraph {
    @Serializable
    data object SignIn: AuthGraph
}

sealed interface HomeGraph {
    @Serializable
    data object RescuedAnimal: HomeGraph
    @Serializable
    data object Favorite: HomeGraph
    @Serializable
    data object MyPage: HomeGraph
}

sealed interface RescuedAnimalGraph {
    @Serializable
    data object RescuedAnimal : RescuedAnimalGraph

    @Serializable
    data class RescuedAnimalDetail(val id: String) : RescuedAnimalGraph
}


sealed interface FavoriteGraph {
    @Serializable
    data object Favorite : FavoriteGraph
}


sealed interface MyPageGraph {
    @Serializable
    data object MyPage : MyPageGraph
}