package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetSidoUseCase
import com.example.domain.usecase.DeleteFavoriteAnimalUseCase
import com.example.domain.usecase.GetRescuedAnimalUseCase
import com.example.domain.usecase.SelectFavoriteAnimalUseCase
import com.example.domain.usecase.InsertFavoriteAnimalUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSidoUseCase: GetSidoUseCase,
    private val getRescuedAnimalUseCase: GetRescuedAnimalUseCase,
    private val selectFavoriteAnimalUseCase: SelectFavoriteAnimalUseCase,
    private val insertFavoriteAnimalUseCase: InsertFavoriteAnimalUseCase,
    private val deleteFavoriteAnimalUseCase: DeleteFavoriteAnimalUseCase
) : ViewModel() {


    suspend fun getSido() = getSidoUseCase().collect { result -> Logger.d("sido: $result") }
    suspend fun getRescuedAnimal() = getRescuedAnimalUseCase(
        pageNo = 1,
        numOfRows = 20
    ).collect { result -> Logger.d("animal: $result") }
}