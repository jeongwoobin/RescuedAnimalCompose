package com.example.presentation.screens.favoriteScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Animal
import com.example.domain.entity.Status
import com.example.domain.usecase.DeleteFavoriteAnimalUseCase
import com.example.domain.usecase.SelectFavoriteAnimalUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.utils.Utils
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val selectFavoriteAnimalUseCase: SelectFavoriteAnimalUseCase,
    private val deleteFavoriteAnimalUseCase: DeleteFavoriteAnimalUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<FavoriteContract.Event, FavoriteContract.State, FavoriteContract.Effect>(savedStateHandle) {

    init {
//        initData()
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): FavoriteContract.State {
        return FavoriteContract.State(
            favoriteAnimalListState = listOf(), loadingState = FavoriteContract.LoadingState.Idle
        )
    }

    override fun handleEvent(event: FavoriteContract.Event) {
        when (event) {
            is FavoriteContract.Event.InitData -> {
                initData()
            }
            is FavoriteContract.Event.LoadMore -> {
                selectFavoriteAnimal()
            }

            is FavoriteContract.Event.OnListItemClicked -> {
                setEffect { FavoriteContract.Effect.Navigation.ToDetail(event.animal) }
            }

            is FavoriteContract.Event.OnItemFavoriteClicked -> {
                deleteFavoriteAnimal(index = event.index, animal = event.animal)
            }

            else -> {}
        }
    }

    private fun initData() {
        selectFavoriteAnimal()
    }

    private fun selectFavoriteAnimal() {
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(loadingState = FavoriteContract.LoadingState.Loading) }
            selectFavoriteAnimalUseCase().let { result ->
                Logger.d("selectFavoriteAnimal result\nstatus: ${result.status}\ndata: ${result.data}\nmessage: ${result.message}")
                when (result.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        result.data?.let { data ->
                            if (data.isNotEmpty()) setState {
                                copy(
                                    favoriteAnimalListState = data
                                )
                            } else {
                                setEffect {
                                    FavoriteContract.Effect.ShowSnackbar(
                                        Utils.snackBarContent(
                                            content = "저장된 데이터가 없습니다."
                                        )
                                    )
                                }
                            }
                        }
                    }

                    else -> {
                        setEffect {
                            FavoriteContract.Effect.ShowSnackbar(
                                Utils.snackBarContent(
                                    isError = true, content = result.message.toString()
                                )
                            )
                        }
                    }
                }
            }
            delay(1000) // 로딩바 안 보여서 추가
            setState { copy(loadingState = FavoriteContract.LoadingState.Idle) }
        }
    }

    private fun deleteFavoriteAnimal(index: Int, animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteAnimalUseCase(
                favoriteAnimal = animal.copy(favorite = false)
            ).onStart { setState { copy(loadingState = FavoriteContract.LoadingState.Loading) } }
                .onCompletion { setState { copy(loadingState = FavoriteContract.LoadingState.Idle) } }
                .collect { result ->
                    Logger.d("deleteFavoriteAnimal result\nstatus: ${result.status}\nmessage: ${result.message}")
                    when (result.status) {
                        Status.LOADING -> {}
                        Status.SUCCESS -> {
                            result.data?.let { data ->
                                if (data) {
                                    setState {
                                        copy(favoriteAnimalListState = currentState.favoriteAnimalListState.toMutableList()
                                            .apply {
                                                removeAt(index)
                                            })
                                    }
                                    setEffect {
                                        FavoriteContract.Effect.ShowSnackbar(
                                            Utils.snackBarContent(
                                                content = "관심목록에서 제거하였습니다."
                                            )
                                        )
                                    }
                                } else {
                                    // 데이터가 없습니다
                                    setEffect {
                                        FavoriteContract.Effect.ShowSnackbar(
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
                                FavoriteContract.Effect.ShowSnackbar(
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