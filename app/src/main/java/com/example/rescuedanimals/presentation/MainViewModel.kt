package com.example.rescuedanimals.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rescuedanimals.domain.usecase.DeleteFavoriteAnimalUseCase
import com.example.rescuedanimals.domain.usecase.GetFavoriteAnimalUseCase
import com.example.rescuedanimals.domain.usecase.GetSidoUseCase
import com.example.rescuedanimals.domain.usecase.InsertFavoriteAnimalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSidoUseCase: GetSidoUseCase,
    private val getFavoriteAnimalUseCase: GetFavoriteAnimalUseCase,
    private val insertFavoriteAnimalUseCase: InsertFavoriteAnimalUseCase,
    private val deleteFavoriteAnimalUseCase: DeleteFavoriteAnimalUseCase
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getSidoUseCase().let { sidoResult ->
//                if (diaryList.isEmpty()) {
//                    Log.d(TAG, "empty diary table")
//                } else {
//                    _diaries.value = diaryList
//                }
            }
        }
    }

//    suspend fun getSido(): Result<SidoEntity> = getSidoUseCase.invoke()
}