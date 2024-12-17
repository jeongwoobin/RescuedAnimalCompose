package com.example.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Sido
import com.example.domain.entity.Status
import com.example.domain.usecase.GetSidoUseCase
import com.example.domain.usecase.DeleteFavoriteAnimalUseCase
import com.example.domain.usecase.GetRescuedAnimalUseCase
import com.example.domain.usecase.SelectFavoriteAnimalUseCase
import com.example.domain.usecase.InsertFavoriteAnimalUseCase
import com.example.presentation.screens.rescuedAnimalScreen.RescuedAnimalContract
import com.example.presentation.utils.Utils
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSidoUseCase: GetSidoUseCase,
) : ViewModel() {

    fun getSido() {
        Logger.d("getSido")
        viewModelScope.launch(Dispatchers.Default) {
            getSidoUseCase.invoke().collect { result ->
                Logger.d("getSido result\nstatus: ${result.status}\nmessage: ${result.message}")
                when (result.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        result.data?.let { data ->
                            MainActivity.sido = listOf(null) + data
                        } ?: { MainActivity.sido = listOf(null) }
                    }

                    else -> {
                    }
                }

            }
        }
    }
}