package com.example.data.datasource.remote

import com.example.data.model.remote.AnimalEntity
import com.example.data.model.remote.AnimalSearchFilterEntity
import com.example.data.model.remote.BaseResponse
import com.example.data.model.remote.ListBody
import com.example.data.model.remote.ShelterEntity
import com.example.data.model.remote.SidoEntity
import com.example.data.model.remote.SigunguEntity
import com.example.domain.entity.AnimalSearchFilter
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RescuedAnimalsDataSource {
    suspend fun getSido(): Flow<Response<BaseResponse<ListBody<SidoEntity>>>>

    suspend fun getSigungu(upr_cd: String): Flow<Response<BaseResponse<ListBody<SigunguEntity>>>>

    suspend fun getShelter(upr_cd: String, org_cd: String): Flow<Response<BaseResponse<ListBody<ShelterEntity>>>>

    suspend fun getRescuedAnimal(
        animalSearchFilter: AnimalSearchFilterEntity
    ): Flow<Response<BaseResponse<ListBody<AnimalEntity>>>>
}