package com.example.presentation.screens.rescuedAnimalScreen.searchFilterScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.entity.Animal
import com.example.domain.entity.Status
import com.example.domain.entity.Upkind
import com.example.domain.usecase.DeleteFavoriteAnimalUseCase
import com.example.domain.usecase.InsertFavoriteAnimalUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.navigation.graph.DetailGraph
import com.example.presentation.navigation.graph.RescuedAnimalGraph
import com.example.presentation.utils.Utils
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFilterViewModel @Inject constructor(
    private val insertFavoriteAnimalUseCase: InsertFavoriteAnimalUseCase,
    private val deleteFavoriteAnimalUseCase: DeleteFavoriteAnimalUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SearchFilterContract.Event, SearchFilterContract.State, SearchFilterContract.Effect>(
    savedStateHandle
) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): SearchFilterContract.State {
        return SearchFilterContract.State(
            filterState = savedStateHandle.toRoute<RescuedAnimalGraph.RescuedAnimal.SearchFilter>(
                typeMap = RescuedAnimalGraph.RescuedAnimal.SearchFilter.typeMap
            ).filter,
        )
    }

    override fun handleEvent(event: SearchFilterContract.Event) {
        when (event) {
            is SearchFilterContract.Event.OnUpkindClicked -> {
                updateKind(event.kind)
            }
//            is SearchFilterContract.Event.OnImageClicked -> {
//
//            }
//
//            is SearchFilterContract.Event.OnCareTelClicked -> {
//
//            }
//
//            is SearchFilterContract.Event.OnItemFavoriteClicked -> {
//                if (event.animal.favorite == true) deleteFavoriteAnimal(
//                    animal = event.animal
//                )
//                else insertFavoriteAnimal(animal = event.animal)
//            }
//
//            else -> {}
        }
    }

    private fun updateKind(upkind: Upkind) {
        setState {
            copy(
                filterState = currentState.filterState.copy(upkind = upkind)
            )
        }
    }
}