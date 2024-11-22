package com.example.presentation.screens.rescuedAnimalScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Animal
import com.example.domain.entity.Event
import com.example.domain.entity.Result
import com.example.domain.entity.Status
import com.example.domain.usecase.DeleteFavoriteAnimalUseCase
import com.example.domain.usecase.SelectFavoriteAnimalUseCase
import com.example.domain.usecase.GetRescuedAnimalUseCase
import com.example.domain.usecase.InsertFavoriteAnimalUseCase
import com.example.presentation.utils.Utils
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RescuedAnimalViewModel2 @Inject constructor(
    private val getRescuedAnimalUseCase: GetRescuedAnimalUseCase,
    private val selectFavoriteAnimalUseCase: SelectFavoriteAnimalUseCase,
    private val insertFavoriteAnimalUseCase: InsertFavoriteAnimalUseCase,
    private val deleteFavoriteAnimalUseCase: DeleteFavoriteAnimalUseCase,
) : ViewModel() {

    private var pageNo: Int = 1

    private val _resultState: MutableStateFlow<Result<Any>> = MutableStateFlow(Result.success())
    val resultState: StateFlow<Result<Any>>
        get() = _resultState.asStateFlow()

    private val _snackbarEvent: MutableStateFlow<Event<String?>> = MutableStateFlow(Event(null))
    val snackbarEvent: StateFlow<Event<String?>>
        get() = _snackbarEvent.asStateFlow()

    private val _rescuedAnimalList: MutableStateFlow<List<Animal>> = MutableStateFlow(emptyList())
    val rescuedAnimalList: StateFlow<List<Animal>>
        get() = _rescuedAnimalList.asStateFlow()

    private val _favoriteAnimalList: MutableLiveData<List<Animal>> = MutableLiveData(emptyList())
    val favoriteAnimalList: LiveData<List<Animal>>
        get() = _favoriteAnimalList.map { list ->
            list.sortedBy { item ->
                item.desertionNo.toIntOrNull() ?: 0
            }
        }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            selectFavoriteAnimal().let {
                getRescuedAnimal(refresh = true)
            }
        }
    }

    private fun updateSnackbarEvent(text: String) {
        _snackbarEvent.update { Event(text) }
    }

    private fun setResultState(state: Result<Any>) {
        if (resultState.value.status != state.status) _resultState.update { state }
    }

    private fun updatePage(refresh: Boolean = false) {
        if (refresh) pageNo = 1
        else {
            pageNo += if (pageNo != 1) 1 else 2
        }
    }

    // DB에 있는 데이터 연동
    private fun favoriteMapper(originList: List<Animal>): List<Animal> {
        val tempFavoriteAnimalList: MutableList<Animal> =
            favoriteAnimalList.value?.toMutableList() ?: mutableListOf()
        val tempList: MutableList<Animal> = mutableListOf()
        if (tempFavoriteAnimalList.isEmpty())
            return originList
        originList.forEachIndexed { _, animal ->
            val tempAnimal = animal.copy()
            tempFavoriteAnimalList.firstOrNull()?.let { favoriteAnimal ->
                if (tempAnimal.desertionNo == favoriteAnimal.desertionNo) {
                    tempAnimal.favorite = true
                    tempFavoriteAnimalList.removeFirstOrNull()
                }
            }
            tempList.add(tempAnimal)
        }
        _favoriteAnimalList.postValue(tempFavoriteAnimalList)
        Logger.d("map lsit: ${tempList.size}, favorite: ${tempFavoriteAnimalList.size}")
        return tempList
    }

    suspend fun getRescuedAnimal(refresh: Boolean = false) {
        if (refresh) updatePage(refresh = refresh)
        viewModelScope.launch(Dispatchers.IO) {
            getRescuedAnimalUseCase(
                pageNo = pageNo, numOfRows = if (pageNo != 1) 20 else 40
            ).onStart { emit(Result.loading(null)) }.collect { result ->
                Logger.d("getRescuedAnimal result status: ${result.status}")
                setResultState(result)
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.let { data ->
                            if (data.items.item.isNotEmpty())
                                _rescuedAnimalList.update {
                                    if (refresh)
                                        favoriteMapper(data.items.item)
                                    else
                                        rescuedAnimalList.value + favoriteMapper(data.items.item)
                                }
                            else {
                                // 데이터가 없습니다
                                updateSnackbarEvent(Utils.snackBarContent(content = "마지막 데이터 입니다."))
                            }
                            updatePage()
                        }
                    }

                    Status.LOADING -> {}
                    else -> {
                        updateSnackbarEvent(
                            Utils.snackBarContent(
                                isError = true, content = result.message.toString()
                            )
                        )
                    }
                }
            }
        }
    }

    suspend fun insertFavoriteAnimal(index: Int, animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteAnimalUseCase(
                favoriteAnimal = animal.copy(favorite = !(animal.favorite ?: false))
            ).onStart { emit(Result.loading(null)) }.collect { result ->
                Logger.d("insertFavoriteAnimal result status: ${result.status}")
                setResultState(result)
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.let { data ->
                            if (data) {
                                _rescuedAnimalList.update { list ->
                                    val updateList = list.toMutableList()
                                    val tempItem = updateList[index]
                                    updateList[index] =
                                        tempItem.copy(favorite = !(tempItem.favorite ?: false))
                                    updateList
                                }
                                updateSnackbarEvent(Utils.snackBarContent(content = if (animal.favorite == true) "관심항목에서 제거하였습니다." else "관심항목에 추가하였습니다."))
                            } else {
                                // 데이터가 없습니다
                                updateSnackbarEvent(
                                    Utils.snackBarContent(
                                        isError = true, content = "저장에 실패했습니다."
                                    )
                                )
                            }
                        }
                    }

                    Status.LOADING -> {}
                    else -> {
                        updateSnackbarEvent(
                            Utils.snackBarContent(
                                isError = true, content = result.message.toString()
                            )
                        )
                    }
                }
            }
        }
    }

    suspend fun deleteFavoriteAnimal(index: Int, animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteAnimalUseCase(animal)
                .onStart { emit(Result.loading(null)) }
                .collect { result ->
                    Logger.d("deleteFavoriteAnimal result status: ${result.status}")
                    setResultState(result)
                    when (result.status) {
                        Status.SUCCESS -> {
                            result.data?.let { data ->
                                if (data)
                                    favoriteAnimalList.value?.let { originList ->
                                    _favoriteAnimalList.postValue(originList.toMutableList().apply { this.removeAt(index) } )
                                    }
                                else {
                                    updateSnackbarEvent(Utils.snackBarContent(content = "이미 삭제된 데이터입니다."))
                                }
                            }
                        }

                        Status.LOADING -> {}
                        else -> {
                            updateSnackbarEvent(
                                Utils.snackBarContent(
                                    isError = true,
                                    content = result.message.toString()
                                )
                            )
                        }
                    }
                }
        }
    }

    private suspend fun selectFavoriteAnimal() {
        viewModelScope.launch(Dispatchers.IO) {
            selectFavoriteAnimalUseCase().onStart { emit(Result.loading(null)) }.collect { result ->
                Logger.d("selectFavoriteAnimal result status: ${result.status}")
                setResultState(result)
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.let { data ->
                            if (data.isNotEmpty()) {
                                _favoriteAnimalList.postValue(data)
                            } else {
                                // 데이터가 없습니다
//                                updateSnackbarEvent(Utils.snackBarContent(content = "저장된 데이터가 없습니다."))
                            }
                        }
                    }

                    Status.LOADING -> {}
                    else -> {
                        updateSnackbarEvent(
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