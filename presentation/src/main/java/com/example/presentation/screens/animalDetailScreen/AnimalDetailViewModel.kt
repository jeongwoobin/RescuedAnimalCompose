package com.example.presentation.screens.animalDetailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.entity.Animal
import com.example.domain.entity.Event
import com.example.domain.entity.Result
import com.example.domain.entity.Status
import com.example.domain.usecase.DeleteFavoriteAnimalUseCase
import com.example.domain.usecase.InsertFavoriteAnimalUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.navigation.graph.DetailGraph
import com.example.presentation.utils.Utils
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalDetailViewModel @Inject constructor(
    private val insertFavoriteAnimalUseCase: InsertFavoriteAnimalUseCase,
    private val deleteFavoriteAnimalUseCase: DeleteFavoriteAnimalUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<AnimalDetailContract.Event, AnimalDetailContract.State, AnimalDetailContract.Effect>(
    savedStateHandle
) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): AnimalDetailContract.State {
        return AnimalDetailContract.State(
            animalState = savedStateHandle.toRoute<DetailGraph.Detail>(
                typeMap = DetailGraph.Detail.typeMap
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
                if (event.animal.favorite == true) deleteFavoriteAnimal(
                    animal = event.animal
                )
                else insertFavoriteAnimal(animal = event.animal)
            }
        }
    }

    private fun insertFavoriteAnimal(animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteAnimalUseCase(
                favoriteAnimal = animal.copy(favorite = true)
            ).onStart { setState { copy(loadingState = AnimalDetailContract.LoadingState.Loading) } }
                .onCompletion { setState { copy(loadingState = AnimalDetailContract.LoadingState.Idle) } }
                .collect { result ->
                    Logger.d("insertFavoriteAnimal result\nstatus: ${result.status}\nmessage: ${result.message}")
                    when (result.status) {
                        Status.LOADING -> {}
                        Status.SUCCESS -> {
                            result.data?.let { data ->
                                if (data) {
                                    setState {
                                        copy(
                                            animalState = currentState.animalState.copy(favorite = true)
                                        )
                                    }
                                    setEffect {
                                        AnimalDetailContract.Effect.ShowSnackbar(
                                            Utils.snackBarContent(
                                                content = "관심목록에 추가하였습니다."
                                            )
                                        )
                                    }
                                } else {
                                    // 데이터가 없습니다
                                    setEffect {
                                        AnimalDetailContract.Effect.ShowSnackbar(
                                            Utils.snackBarContent(
                                                isError = true, content = "관심목록 추가에 실패했습니다."
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        else -> {
                            setEffect {
                                AnimalDetailContract.Effect.ShowSnackbar(
                                    Utils.snackBarContent(
                                        isError = true, content = result.message.toString()
                                    )
                                )
                            }
                        }
                    }

                }
        }
    }

    private fun deleteFavoriteAnimal(animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteAnimalUseCase(
                favoriteAnimal = animal.copy(favorite = false)
            ).onStart { setState { copy(loadingState = AnimalDetailContract.LoadingState.Loading) } }
                .onCompletion { setState { copy(loadingState = AnimalDetailContract.LoadingState.Idle) } }
                .collect { result ->
                    Logger.d("deleteFavoriteAnimal result\nstatus: ${result.status}\nmessage: ${result.message}")
                    when (result.status) {
                        Status.LOADING -> {}
                        Status.SUCCESS -> {
                            result.data?.let { data ->
                                if (data) {
                                    setState {
                                        copy(
                                            animalState = currentState.animalState.copy(favorite = false)
                                        )
                                    }
                                    setEffect {
                                        AnimalDetailContract.Effect.ShowSnackbar(
                                            Utils.snackBarContent(
                                                content = "관심목록에서 제거하였습니다."
                                            )
                                        )
                                    }
                                } else {
                                    // 데이터가 없습니다
                                    setEffect {
                                        AnimalDetailContract.Effect.ShowSnackbar(
                                            Utils.snackBarContent(
                                                isError = true, content = "관심목록 삭제에 실패하였습니다."
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        else -> {
                            setEffect {
                                AnimalDetailContract.Effect.ShowSnackbar(
                                    Utils.snackBarContent(
                                        isError = true, content = result.message.toString()
                                    )
                                )
                            }
                        }
                    }

                }
        }
    }
}