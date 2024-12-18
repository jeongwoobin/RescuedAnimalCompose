package com.example.presentation.screens.rescuedAnimalScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Animal
import com.example.domain.entity.AnimalSearchFilter
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
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<RescuedAnimalContract.Event, RescuedAnimalContract.State, RescuedAnimalContract.Effect>(
    savedStateHandle
) {

    init {
//        initData()
    }

    /**
     * Create initial State of Views
     */
    override fun createInitialState(savedStateHandle: SavedStateHandle): RescuedAnimalContract.State {
        return RescuedAnimalContract.State(
            filterState = AnimalSearchFilter(),
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
                initData(event.filter)
            }

            is RescuedAnimalContract.Event.LoadMore -> {
                if (event.refresh) {
                    selectFavoriteAnimal(refresh = event.refresh)
                } else {
                    getRescuedAnimal(refresh = event.refresh)
                }
            }

            is RescuedAnimalContract.Event.OnFabClicked -> {
                setEffect { RescuedAnimalContract.Effect.ScrollUp }
            }

            is RescuedAnimalContract.Event.OnFilterClicked -> {
                setEffect { RescuedAnimalContract.Effect.Navigation.ToFilter(event.filter) }
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

    private fun initData(filter: AnimalSearchFilter?) {
        var refreshState = currentState.rescuedAnimalListState.isEmpty()
        filter?.let {
            refreshState = refreshState || currentState.filterState != it
            setSearchFilter(it)
        }
        selectFavoriteAnimal(refresh = refreshState)
    }

    private fun setSearchFilter(filter: AnimalSearchFilter) {
        Logger.d("setSearchFilter\ncurrent: ${currentState.filterState}\nnew: $filter\nequal?: ${currentState.filterState == filter}")
        setState {
            copy(filterState = filter)
        }
    }

    private fun updatePage(refresh: Boolean = false) {
        Logger.d("updatePage: $refresh")
        var state = currentState.filterState.pageNo
        if (refresh) state = 1
        else {
            state += if (state != 1) 1 else 2
        }

        setState {
            copy(filterState = currentState.filterState.copy(pageNo = state))
        }
    }

    /*
        새로 불러온 rescuedList와 favoriteList 비교 후 favorite 수정된 rescuedList return
     */
    private fun syncLocalAndRemoteList(
        favoriteList: List<Animal>, rescuedList: List<Animal>
    ): List<Animal> {
        Logger.d("syncLocalAndRemoteList")
        if (favoriteList.isEmpty()
//            || (rescuedList.last().desertionNo > favoriteList.first().desertionNo)
        ) return rescuedList
        val tempFavoriteList = favoriteList.toMutableList()
        // favorite에서 삭제 후 돌아왔을 때 새로 데이터를 불러오지 않기 위해 false로 초기화
        val tempRescuedList = rescuedList.toMutableList().apply {
            for (i in 0 until this.size) {
                var item = this[i].copy(favorite = false)

                for (favoriteAnimal in tempFavoriteList) {
                    if (item.desertionNo == favoriteAnimal.desertionNo) {
                        item = item.copy(favorite = true)
                        tempFavoriteList.remove(favoriteAnimal)
                        break
                    }
                }
                this[i] = item
            }

//            for (animal in this) {
//                for (favoriteAnimal in tempFavoriteList) {
//                    if (animal.desertionNo == favoriteAnimal.desertionNo) {
//                        animal.favorite = true
//                        tempFavoriteList.remove(favoriteAnimal)
//                        break
//                    }
//                }
//                // 서버에서 받은 리스트가 sort가 되어있을 때 사용..
////            if (animal.desertionNo == tempFavoriteList.first().desertionNo) {
////                animal.favorite = true
////                tempFavoriteList.removeFirst()
////            }
//                if (tempFavoriteList.isEmpty()) break
//            }
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
        if (refresh) {
            setEvent(RescuedAnimalContract.Event.OnFabClicked)
            updatePage(refresh = true)
        }
        viewModelScope.launch(Dispatchers.Default) {
            getRescuedAnimalUseCase.invoke(
                animalSearchFilter = currentState.filterState
            )
                .onStart { setState { copy(loadingState = RescuedAnimalContract.LoadingState.Loading) } }
                .onCompletion {
                    setState { copy(loadingState = RescuedAnimalContract.LoadingState.Idle) }
//                    if (refresh) {
//                        setEvent(RescuedAnimalContract.Event.OnFabClicked)
//                    }
                    Logger.d("getRescuedAnimal completion")
                }.collect { result ->
                    Logger.d("getRescuedAnimal result\nstatus: ${result.status}\nmessage: ${result.message}")
                    when (result.status) {
                        Status.LOADING -> {}
                        Status.SUCCESS -> {
                            result.data?.let { data ->
                                data.items.item.let { item ->
                                    if (item.isNullOrEmpty()) {
                                        if (refresh) {
                                            setState {
                                                copy(
                                                    rescuedAnimalListState = listOf()
                                                )
                                            }
                                        }
                                        setEffect {
                                            RescuedAnimalContract.Effect.ShowSnackbar(
                                                Utils.snackBarContent(
                                                    content = "마지막 데이터 입니다."
                                                )
                                            )
                                        }
                                    } else {
                                        val list = syncLocalAndRemoteList(
                                            favoriteList = currentState.favoriteAnimalListState,
                                            rescuedList = item
                                        )
                                        setState {
                                            copy(
                                                rescuedAnimalListState = if (refresh) list
                                                else currentState.rescuedAnimalListState + list
                                            )
                                        }
                                    }
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
                                        copy(
                                            rescuedAnimalListState = currentState.rescuedAnimalListState.toMutableList()
                                                .apply {
                                                    this[index] = this[index].copy(favorite = true)
                                                },
                                            favoriteAnimalListState = currentState.favoriteAnimalListState + listOf(
                                                animal.copy(favorite = true)
                                            )
                                        )
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
                                            favoriteAnimalListState = currentState.favoriteAnimalListState.toMutableList()
                                                .apply {
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

    private fun selectFavoriteAnimal(refresh: Boolean) {
        Logger.d("selectFavoriteAnimal $refresh")
        setState { copy(loadingState = RescuedAnimalContract.LoadingState.Loading) }
        viewModelScope.launch(Dispatchers.IO) {
            selectFavoriteAnimalUseCase().let { result ->
                Logger.d("selectFavoriteAnimal result\nstatus: ${result.status}\ndata: ${result.data}\nmessage: ${result.message}")
                when (result.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        result.data?.let { data ->
                            if (data.isNotEmpty()) {
                                if (refresh) {
                                    setState {
                                        copy(
                                            favoriteAnimalListState = data
                                        )
                                    }
                                } else {
                                    val list = syncLocalAndRemoteList(
                                        favoriteList = data,
                                        rescuedList = currentState.rescuedAnimalListState
                                    )
                                    setState {
                                        copy(
                                            rescuedAnimalListState = list,
                                        )
                                    }
//                                    Logger.d("currentState First: ${currentState.rescuedAnimalListState.first()}")
                                }
                            }

                        }
                    }

                    else -> {}
                }
                if (refresh) {
                    getRescuedAnimal(refresh = refresh)
                } else {
                    setState { copy(loadingState = RescuedAnimalContract.LoadingState.Idle) }
                }
            }
        }
    }
}