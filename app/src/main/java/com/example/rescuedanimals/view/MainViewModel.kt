package com.example.rescuedanimals.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rescuedanimals.domain.usecase.GetSidoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSidoUseCase: GetSidoUseCase,
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getSidoUseCase.invoke().let { sidoResult ->
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