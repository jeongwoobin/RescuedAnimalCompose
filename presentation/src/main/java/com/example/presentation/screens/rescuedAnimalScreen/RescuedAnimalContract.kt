package com.example.presentation.screens.rescuedAnimalScreen

import com.example.domain.entity.Animal
import com.example.presentation.base.UiEffect
import com.example.presentation.base.UiEvent
import com.example.presentation.base.UiState

class RescuedAnimalContract {

    // Events that user performed
    sealed class Event : UiEvent {
        data class LoadMore(val refresh: Boolean) : Event()
        data object OnFilterClicked : Event()
        data class OnListItemClicked(val id: String) : Event()
    }

    // Ui View States
    data class State(
        val pageState: Int,
        val rescuedAnimalListState: List<Animal>,
        val loadingState: LoadingState
    ) : UiState

    // View State that related to Rescued Animal
    sealed class LoadingState {
        data object Idle : LoadingState()
        data object Loading : LoadingState()
    }

    // Side effects
    sealed class Effect : UiEffect {
        data class ShowSnackbar(val content: String) : Effect()
        sealed class Navigation : Effect() {
            data class ToDetail(val id: String) : Navigation()
            data object ToFilter : Navigation()
        }
    }
}