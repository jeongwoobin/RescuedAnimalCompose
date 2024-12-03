package com.example.presentation.screens.animalDetailScreen

import com.example.domain.entity.Animal
import com.example.presentation.base.UiEffect
import com.example.presentation.base.UiEvent
import com.example.presentation.base.UiState

class AnimalDetailContract {

    // Events that user performed
    sealed class Event : UiEvent {
        data class OnImageClicked(val image: String) : Event()
        data class OnCareTelClicked(val tel: String) : Event()
        data class OnItemFavoriteClicked(val index: Int, val animal: Animal) : Event()
    }

    // Ui View States
    data class State(
        val animalState: Animal,
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
            data class ToImage(val image: String) : Navigation()
        }
    }
}