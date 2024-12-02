package com.example.presentation.screens.rescuedAnimalScreen

import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Animal
import com.example.domain.entity.Status
import com.example.domain.usecase.DeleteFavoriteAnimalUseCase
import com.example.domain.usecase.SelectFavoriteAnimalUseCase
import com.example.domain.usecase.GetRescuedAnimalUseCase
import com.example.domain.usecase.InsertFavoriteAnimalUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.utils.Utils
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RescuedAnimalViewModel @Inject constructor(
    private val getRescuedAnimalUseCase: GetRescuedAnimalUseCase,
    private val selectFavoriteAnimalUseCase: SelectFavoriteAnimalUseCase,
    private val insertFavoriteAnimalUseCase: InsertFavoriteAnimalUseCase,
    private val deleteFavoriteAnimalUseCase: DeleteFavoriteAnimalUseCase,
) : BaseViewModel<RescuedAnimalContract.Event, RescuedAnimalContract.State, RescuedAnimalContract.Effect>() {

    init {
//        initData()
    }

    /**
     * Create initial State of Views
     */
    override fun createInitialState(): RescuedAnimalContract.State {
        return RescuedAnimalContract.State(
            pageState = 1,
            rescuedAnimalListState = listOf(),
            favoriteAnimalListState = listOf(),
            loadingState = RescuedAnimalContract.LoadingState.Idle
        )
    }

    /**
     * Handle each event
     */
    override fun handleEvent(event: RescuedAnimalContract.Event) {
        when (event) {
            is RescuedAnimalContract.Event.InitData -> {
                initData()
            }

            is RescuedAnimalContract.Event.LoadMore -> {
                if(event.refresh) {
                    selectFavoriteAnimal()
                }else {
                    getRescuedAnimal(refresh = event.refresh)
                }
            }

            is RescuedAnimalContract.Event.OnFilterClicked -> {
                setEffect { RescuedAnimalContract.Effect.Navigation.ToFilter }
            }

            is RescuedAnimalContract.Event.OnListItemClicked -> {
                setEffect { RescuedAnimalContract.Effect.Navigation.ToDetail(event.animal) }
            }

            is RescuedAnimalContract.Event.OnItemFavoriteClicked -> {
                if (event.animal.favorite == true) deleteFavoriteAnimal(
                    index = event.index, animal = event.animal
                )
                else insertFavoriteAnimal(index = event.index, animal = event.animal)
            }
        }
    }

    private fun initData() {
        selectFavoriteAnimal()
    }

    private fun updatePage(refresh: Boolean = false) {
        var state = currentState.pageState
        if (refresh) state = 1
        else {
            state += if (state != 1) 1 else 2
        }

        setState {
            copy(pageState = state)
        }
    }

    /*
        새로 불러온 rescuedList와 favoriteList 비교 후 favorite 수정된 rescuedList return
     */
    private fun syncLocalAndRemoteList(
        favoriteList: List<Animal>, rescuedList: List<Animal>
    ): List<Animal> {
        Logger.d("syncLocalAndRemoteList")
        if (favoriteList.isEmpty() || (rescuedList.last().desertionNo > favoriteList.first().desertionNo)) return rescuedList
        val tempFavoriteList = favoriteList.toMutableList()
        val tempRescuedList = rescuedList.toMutableList()
        for (animal in tempRescuedList) {
            if (animal.desertionNo == tempFavoriteList.first().desertionNo) {
                animal.favorite = true
                tempFavoriteList.removeFirst()
            }
            if (tempFavoriteList.isEmpty()) break
        }
        setState {
            copy(
                favoriteAnimalListState = tempFavoriteList
            )
        }
        return tempRescuedList
    }

    private fun getRescuedAnimal(refresh: Boolean = false) {
        Logger.d("getRescuedAnimal")
        if (refresh) updatePage(refresh = true)
        viewModelScope.launch(Dispatchers.Default) {
            getRescuedAnimalUseCase(
                pageNo = currentState.pageState,
                numOfRows = if (currentState.pageState != 1) 20 else 40
            ).onStart { setState { copy(loadingState = RescuedAnimalContract.LoadingState.Loading) } }
                .onCompletion {
                    setState { copy(loadingState = RescuedAnimalContract.LoadingState.Idle) }
                    Logger.d("getRescuedAnimal completion")
                }.collect { result ->
                    Logger.d("getRescuedAnimal result\nstatus: ${result.status}\nmessage: ${result.message}")
                    when (result.status) {
                        Status.LOADING -> {}
                        Status.SUCCESS -> {
                            result.data?.let { data ->
                                if (data.items.item.isNotEmpty()) {
                                    val list = syncLocalAndRemoteList(
                                        favoriteList = currentState.favoriteAnimalListState,
                                        rescuedList = data.items.item
                                    )
                                    setState {
                                        copy(
                                            rescuedAnimalListState = if (refresh) list
                                            else currentState.rescuedAnimalListState + list
                                        )
                                    }
                                } else {
                                    // 데이터가 없습니다
                                    setEffect {
                                        RescuedAnimalContract.Effect.ShowSnackbar(
                                            Utils.snackBarContent(
                                                content = "마지막 데이터 입니다."
                                            )
                                        )
                                    }
//                                    updateSnackbarEvent()
                                }
                                updatePage()
                            }
                        }

                        else -> {
                            setEffect {
                                RescuedAnimalContract.Effect.ShowSnackbar(
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

    private fun insertFavoriteAnimal(index: Int, animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteAnimalUseCase(
                favoriteAnimal = animal.copy(favorite = true)
            ).onStart { setState { copy(loadingState = RescuedAnimalContract.LoadingState.Loading) } }
                .onCompletion { setState { copy(loadingState = RescuedAnimalContract.LoadingState.Idle) } }
                .collect { result ->
                    Logger.d("insertFavoriteAnimal result\nstatus: ${result.status}\nmessage: ${result.message}")
                    when (result.status) {
                        Status.LOADING -> {}
                        Status.SUCCESS -> {
                            result.data?.let { data ->
                                if (data) {
                                    setState {
                                        copy(rescuedAnimalListState = currentState.rescuedAnimalListState.toMutableList()
                                            .apply {
                                                this[index] = this[index].copy(favorite = true)
                                            },
                                            favoriteAnimalListState = currentState.favoriteAnimalListState + listOf(animal.copy(favorite = true)))
                                    }
                                    setEffect {
                                        RescuedAnimalContract.Effect.ShowSnackbar(
                                            Utils.snackBarContent(
                                                content = "관심목록에 추가하였습니다."
                                            )
                                        )
                                    }
                                } else {
                                    // 데이터가 없습니다
                                    setEffect {
                                        RescuedAnimalContract.Effect.ShowSnackbar(
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
                                RescuedAnimalContract.Effect.ShowSnackbar(
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

    private fun deleteFavoriteAnimal(index: Int, animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteAnimalUseCase(
                favoriteAnimal = animal.copy(favorite = false)
            ).onStart { setState { copy(loadingState = RescuedAnimalContract.LoadingState.Loading) } }
                .onCompletion { setState { copy(loadingState = RescuedAnimalContract.LoadingState.Idle) } }
                .collect { result ->
                    Logger.d("deleteFavoriteAnimal result\nstatus: ${result.status}\nmessage: ${result.message}")
                    when (result.status) {
                        Status.LOADING -> {}
                        Status.SUCCESS -> {
                            result.data?.let { data ->
                                if (data) {
                                    setState {
                                        copy(rescuedAnimalListState = currentState.rescuedAnimalListState.toMutableList()
                                            .apply {
                                                this[index] = this[index].copy(favorite = false)
                                            },
                                            favoriteAnimalListState = currentState.favoriteAnimalListState.toMutableList().apply {
                                                remove(animal)
                                            })
                                    }
                                    setEffect {
                                        RescuedAnimalContract.Effect.ShowSnackbar(
                                            Utils.snackBarContent(
                                                content = "관심목록에서 제거하였습니다."
                                            )
                                        )
                                    }
                                } else {
                                    // 데이터가 없습니다
                                    setEffect {
                                        RescuedAnimalContract.Effect.ShowSnackbar(
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
                                RescuedAnimalContract.Effect.ShowSnackbar(
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

    private fun selectFavoriteAnimal() {
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(loadingState = RescuedAnimalContract.LoadingState.Loading) }
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
                            }
                        }
                    }

                    else -> {}
                }
                getRescuedAnimal(refresh = true)
            }
        }
    }
}