package com.example.presentation.screens.favoriteScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rescuedanimals.domain.entity.Animal
import com.example.rescuedanimals.domain.entity.Event
import com.example.rescuedanimals.domain.entity.Result
import com.example.rescuedanimals.domain.entity.Status
import com.example.rescuedanimals.domain.usecase.DeleteFavoriteAnimalUseCase
import com.example.rescuedanimals.domain.usecase.SelectFavoriteAnimalUseCase
import com.example.rescuedanimals.domain.usecase.InsertFavoriteAnimalUseCase
import com.example.rescuedanimals.presentation.utils.Utils
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
class FavoriteViewModel @Inject constructor(
    private val selectFavoriteAnimalUseCase: SelectFavoriteAnimalUseCase,
    private val insertFavoriteAnimalUseCase: InsertFavoriteAnimalUseCase,
    private val deleteFavoriteAnimalUseCase: DeleteFavoriteAnimalUseCase
) : ViewModel() {

    private val _resultState: MutableStateFlow<Result<Any>> = MutableStateFlow(Result.success())
    val resultState: StateFlow<Result<Any>>
        get() = _resultState.asStateFlow()

    private val _snackbarEvent: MutableStateFlow<Event<String?>> = MutableStateFlow(Event(null))
    val snackbarEvent: StateFlow<Event<String?>>
        get() = _snackbarEvent.asStateFlow()

    private val _favoriteAnimalList: MutableStateFlow<List<Animal>> = MutableStateFlow(emptyList())
    val favoriteAnimalList: StateFlow<List<Animal>>
        get() = _favoriteAnimalList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            selectFavoriteAnimal()
        }
    }

    private fun updateSnackbarEvent(text: String) {
        _snackbarEvent.update { Event(text) }
    }

    private fun setResultState(state: Result<Any>) {
        try {
            if ((resultState.value as Result).status != state.status)
                _resultState.update { state }
        } catch(e: Exception) {
        }
    }

    suspend fun selectFavoriteAnimal() {
        viewModelScope.launch(Dispatchers.IO) {
            selectFavoriteAnimalUseCase()
                .onStart { emit(Result.loading(null)) }
                .collect { result ->
                    Logger.d("selectFavoriteAnimal result status: ${result.status}")
                    setResultState(result)
                    when (result.status) {
                        Status.SUCCESS -> {
                            result.data?.let { data ->
                                if (data.isNotEmpty())
                                    _favoriteAnimalList.update { data }
                                else {
                                    // 데이터가 없습니다
                                    updateSnackbarEvent(Utils.snackBarContent(content = "저장된 데이터가 없습니다."))
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
                                    _favoriteAnimalList.update { favoriteAnimalList.value.toMutableList().apply { this.removeAt(index) } }
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
}