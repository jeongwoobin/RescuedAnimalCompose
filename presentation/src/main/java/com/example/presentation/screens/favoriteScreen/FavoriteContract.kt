package com.example.presentation.screens.favoriteScreen

import com.example.domain.entity.Animal
import com.example.presentation.base.UiEffect
import com.example.presentation.base.UiEvent
import com.example.presentation.base.UiState

class FavoriteContract {

    // Events that user performed
    sealed class Event : UiEvent {
        data object InitData : Event()
        data class LoadMore(val refresh: Boolean) : Event()
        data object OnFilterClicked : Event()
        data class OnListItemClicked(val animal: Animal) : Event()
        data class OnItemFavoriteClicked(val index: Int, val animal: Animal) : Event()
    }

    // Ui View States
    data class State(
//        val pageState: Int,
        val favoriteAnimalListState: List<Animal>,
        val loadingState: LoadingState
    ) : UiState

    // View State that related to Screen
    sealed class LoadingState {
        data object Idle : LoadingState()
        data object Loading : LoadingState()
    }

    // Side effects
    sealed class Effect : UiEffect {
        data class ShowSnackbar(val content: String) : Effect()
        sealed class Navigation : Effect() {
            data class ToDetail(val animal: Animal) : Navigation()
            data object ToFilter : Navigation()
        }
    }
}