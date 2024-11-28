package com.example.presentation.screens.animalDetailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.domain.entity.Animal
import com.example.domain.entity.Event
import com.example.domain.entity.Result
import com.example.presentation.navigation.graph.RescuedAnimalGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AnimalDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val animal: Animal =
        savedStateHandle.toRoute<RescuedAnimalGraph.RescuedAnimal.RescuedAnimalDetail>(typeMap = RescuedAnimalGraph.RescuedAnimal.RescuedAnimalDetail.typeMap).animal

    private val _resultState: MutableStateFlow<Result<Any>> = MutableStateFlow(Result.success())
    val resultState: StateFlow<Result<Any>>
        get() = _resultState.asStateFlow()

    private val _snackbarEvent: MutableStateFlow<Event<String?>> = MutableStateFlow(Event(null))
    val snackbarEvent: StateFlow<Event<String?>>
        get() = _snackbarEvent.asStateFlow()
}