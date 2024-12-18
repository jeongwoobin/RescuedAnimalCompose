package com.example.presentation.screens.rescuedAnimalScreen.searchFilterScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.entity.AnimalSearchFilter
import com.example.domain.entity.Neuter
import com.example.domain.entity.Shelter
import com.example.domain.entity.Sido
import com.example.domain.entity.Sigungu
import com.example.domain.entity.State
import com.example.domain.entity.Status
import com.example.domain.entity.Upkind
import com.example.domain.usecase.DeleteFavoriteAnimalUseCase
import com.example.domain.usecase.GetShelterUseCase
import com.example.domain.usecase.GetSigunguUseCase
import com.example.domain.usecase.InsertFavoriteAnimalUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.navigation.graph.RescuedAnimalGraph
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
class SearchFilterViewModel @Inject constructor(
    private val getSigunguUseCase: GetSigunguUseCase,
    private val getShelterUseCase: GetShelterUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SearchFilterContract.Event, SearchFilterContract.State, SearchFilterContract.Effect>(
    savedStateHandle
) {

    init {
        currentState.filterState.let { filterState ->
            filterState.upr_cd?.let {
                getSigungu(upr_cd = it.orgCd)
            }
            filterState.org_cd?.let {
                getShelter(upr_cd = it.uprCd, org_cd = it.orgCd)
            }
        }
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): SearchFilterContract.State {
        return SearchFilterContract.State(
            filterState = savedStateHandle.toRoute<RescuedAnimalGraph.RescuedAnimal.SearchFilter>(
                typeMap = RescuedAnimalGraph.RescuedAnimal.SearchFilter.typeMap
            ).filter,
            sigunguListState = null,
            shelterListState = null,
            loadingState = SearchFilterContract.LoadingState.Idle
        )
    }

    override fun handleEvent(event: SearchFilterContract.Event) {
        when (event) {
            is SearchFilterContract.Event.OnFilterInitClicked -> {
                setFilterInit()
            }

            is SearchFilterContract.Event.OnUpkindClicked -> {
                updateKind(upkind = event.upkind)
            }

            is SearchFilterContract.Event.OnNeuterClicked -> {
                updateNeuter(neuter = event.neuter)
            }

            is SearchFilterContract.Event.OnStateClicked -> {
                updateState(state = event.state)
            }

            is SearchFilterContract.Event.OnSidoClicked -> {
                updateSido(sido = event.sido)
            }

            is SearchFilterContract.Event.OnSigunguClicked -> {
                updateSigungu(sigungu = event.sigungu)
            }

            is SearchFilterContract.Event.OnShelterClicked -> {
                updateShelter(shelter = event.shelter)
            }

            is SearchFilterContract.Event.OnDateInitClicked -> {
                updateDateInit()
            }

            is SearchFilterContract.Event.OnDateChanged -> {
                updateDate(isStartDate = event.isStartDate, date = event.date)
            }
        }
    }

    private fun setFilterInit() {
        setState {
            copy(
                filterState = AnimalSearchFilter(),
                sigunguListState = null,
                shelterListState = null,
            )
        }
    }

    private fun updateKind(upkind: Upkind) {
        setState {
            copy(
                filterState = currentState.filterState.copy(upkind = upkind)
            )
        }
    }

    private fun updateNeuter(neuter: Neuter) {
        setState {
            copy(
                filterState = currentState.filterState.copy(neuter = neuter)
            )
        }
    }

    private fun updateState(state: State) {
        setState {
            copy(
                filterState = currentState.filterState.copy(state = state)
            )
        }
    }

    private fun updateSido(sido: Sido?) {
        val tempFilterState = currentState.filterState
        setState {
            copy(
                filterState = currentState.filterState.copy(
                    upr_cd = sido,
                    org_cd = if (tempFilterState.upr_cd == sido) tempFilterState.org_cd else null,
                    care_reg_no = if (tempFilterState.upr_cd == sido) tempFilterState.care_reg_no else null
                )
            )
        }
        sido?.let {
            if (tempFilterState.upr_cd != sido) {
                getSigungu(it.orgCd)
            }
        } ?: {
            setState {
                copy(sigunguListState = null, shelterListState = null)
            }
        }
    }

    private fun updateSigungu(sigungu: Sigungu?) {
        val tempFilterState = currentState.filterState
        setState {
            copy(
                filterState = currentState.filterState.copy(
                    org_cd = sigungu,
                    care_reg_no = if (tempFilterState.org_cd == sigungu) tempFilterState.care_reg_no else null
                )
            )
        }
        sigungu?.let {
            if (tempFilterState.org_cd != sigungu) {
                getShelter(it.uprCd, it.orgCd)
            }
        } ?: {
            setState {
                copy(shelterListState = null)
            }
        }
    }

    private fun updateShelter(shelter: Shelter?) {
        setState {
            copy(
                filterState = currentState.filterState.copy(care_reg_no = shelter)
            )
        }
    }

    private fun updateDateInit() {
        setState {
            copy(filterState = currentState.filterState.copy(bgnde = null, endde = null))
        }
    }

    private fun updateDate(isStartDate: Boolean, date: String?) {
        Logger.d("updateDate\nisStartDate: $isStartDate\ndate: $date")
        setState {
            if (isStartDate) {
                copy(
                    filterState = currentState.filterState.copy(bgnde = date)
                )
            } else {
                copy(
                    filterState = currentState.filterState.copy(endde = date)
                )
            }
        }
    }

    private fun getSigungu(upr_cd: String) {
        Logger.d("getSigungu")
        viewModelScope.launch(Dispatchers.Default) {
            getSigunguUseCase.invoke(
                upr_cd = upr_cd
            )
                .onStart { setState { copy(loadingState = SearchFilterContract.LoadingState.Loading) } }
                .onCompletion {
                    setState { copy(loadingState = SearchFilterContract.LoadingState.Idle) }
                    Logger.d("getSigungu completion")
                }.collect { result ->
                    Logger.d("getSigungu result\nstatus: ${result.status}\nmessage: ${result.message}")
                    when (result.status) {
                        Status.LOADING -> {}
                        Status.SUCCESS -> {
                            result.data?.let { data ->
                                setState {
                                    copy(
                                        sigunguListState = listOf(null) + data
                                    )
                                }
                            }
                        }

                        else -> {
                            setEffect {
                                SearchFilterContract.Effect.ShowSnackbar(
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

    private fun getShelter(upr_cd: String, org_cd: String) {
        Logger.d("getShelter")
        viewModelScope.launch(Dispatchers.Default) {
            getShelterUseCase.invoke(
                upr_cd = upr_cd, org_cd = org_cd
            )
                .onStart { setState { copy(loadingState = SearchFilterContract.LoadingState.Loading) } }
                .onCompletion {
                    setState { copy(loadingState = SearchFilterContract.LoadingState.Idle) }
                    Logger.d("getShelter completion")
                }.collect { result ->
                    Logger.d("getShelter result\nstatus: ${result.status}\nmessage: ${result.message}")
                    when (result.status) {
                        Status.LOADING -> {}
                        Status.SUCCESS -> {
                            result.data?.let { data ->
                                setState {
                                    copy(
                                        shelterListState = listOf(null) + data
                                    )
                                }
                            }
                        }

                        else -> {
                            setEffect {
                                SearchFilterContract.Effect.ShowSnackbar(
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