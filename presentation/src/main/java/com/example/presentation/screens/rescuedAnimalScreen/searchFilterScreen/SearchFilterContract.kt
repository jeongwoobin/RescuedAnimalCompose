package com.example.presentation.screens.rescuedAnimalScreen.searchFilterScreen

import com.example.domain.entity.AnimalSearchFilter
import com.example.domain.entity.Neuter
import com.example.domain.entity.Shelter
import com.example.domain.entity.Sido
import com.example.domain.entity.Sigungu
import com.example.domain.entity.State
import com.example.domain.entity.Upkind
import com.example.presentation.base.UiEffect
import com.example.presentation.base.UiEvent
import com.example.presentation.base.UiState

class SearchFilterContract {

    // Events that user performed
    sealed class Event : UiEvent {
        data class OnUpkindClicked(val upkind: Upkind) : Event()
        data class OnNeuterClicked(val neuter: Neuter) : Event()
        data class OnStateClicked(val state: com.example.domain.entity.State) : Event()
        data class OnSidoClicked(val sido: Sido?) : Event()
        data class OnSigunguClicked(val sigungu: Sigungu?) : Event()
        data class OnShelterClicked(val shelter: Shelter?) : Event()
        data object OnDateInitClicked : Event()
        data class OnDateChanged(val isStartDate: Boolean, val date: String?) : Event()
//        data class OnImageClicked(val image: String) : Event()
//        data class OnCareTelClicked(val tel: String) : Event()
//        data class OnItemFavoriteClicked(val animal: Animal) : Event()
    }

    // Ui View States
    data class State(
        val filterState: AnimalSearchFilter,
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
//            data class ToImage(val image: String) : Navigation()
        }
    }
}