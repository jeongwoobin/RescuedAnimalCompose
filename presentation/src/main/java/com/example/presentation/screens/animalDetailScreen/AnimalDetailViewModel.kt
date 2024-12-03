package com.example.presentation.screens.animalDetailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.domain.entity.Animal
import com.example.domain.entity.Event
import com.example.domain.entity.Result
import com.example.presentation.base.BaseViewModel
import com.example.presentation.navigation.graph.RescuedAnimalGraph
import com.example.presentation.screens.favoriteScreen.FavoriteContract
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AnimalDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<AnimalDetailContract.Event, AnimalDetailContract.State, AnimalDetailContract.Effect>(
    savedStateHandle
) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): AnimalDetailContract.State {
        return AnimalDetailContract.State(
            animalState = savedStateHandle.toRoute<RescuedAnimalGraph.RescuedAnimal.RescuedAnimalDetail>(
                typeMap = RescuedAnimalGraph.RescuedAnimal.RescuedAnimalDetail.typeMap
            ).animal, loadingState = AnimalDetailContract.LoadingState.Idle
        )
    }

    override fun handleEvent(event: AnimalDetailContract.Event) {
        when (event) {
            is AnimalDetailContract.Event.OnImageClicked -> {

            }

            is AnimalDetailContract.Event.OnCareTelClicked -> {

            }

            is AnimalDetailContract.Event.OnItemFavoriteClicked -> {

            }
        }
    }
}