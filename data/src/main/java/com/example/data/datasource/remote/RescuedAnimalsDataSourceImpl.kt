package com.example.data.datasource.remote

import com.example.data.model.remote.AnimalEntity
import com.example.data.service.RescuedAnimalsApi
import com.example.data.model.remote.BaseResponse
import com.example.data.model.remote.ListBody
import com.example.data.model.remote.SidoEntity
import com.example.domain.entity.AnimalSearchFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RescuedAnimalsDataSourceImpl @Inject constructor(
    private val api: RescuedAnimalsApi
) : RescuedAnimalsDataSource {

    override suspend fun getSido(): Flow<Response<BaseResponse<ListBody<SidoEntity>>>> =
        flow { emit(api.fetchSido()) }

    override suspend fun getRescuedAnimal(
        animalSearchFilter: AnimalSearchFilter
    ): Flow<Response<BaseResponse<ListBody<AnimalEntity>>>> = flow {
        emit(
            api.fetchRescuedAnimal(
                bgnde = animalSearchFilter.bgnde,
                endde = animalSearchFilter.endde,
                upkind = animalSearchFilter.upkind?.id,
                neuter = animalSearchFilter.neuter?.neuter,
                pageNo = animalSearchFilter.pageNo,
                numOfRows = animalSearchFilter.numOfRows
            )
        )
    }
}