package com.example.data.datasource.remote

import com.example.data.model.remote.AnimalEntity
import com.example.data.model.remote.AnimalSearchFilterEntity
import com.example.data.model.remote.BaseResponse
import com.example.data.model.remote.ListBodyEntity
import com.example.data.model.remote.ShelterEntity
import com.example.data.model.remote.SidoEntity
import com.example.data.model.remote.SigunguEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RescuedAnimalsDataSource {
    suspend fun getSido(): Flow<Response<BaseResponse<ListBodyEntity<SidoEntity>>>>

    suspend fun getSigungu(upr_cd: String): Flow<Response<BaseResponse<ListBodyEntity<SigunguEntity>>>>

    suspend fun getShelter(upr_cd: String, org_cd: String): Flow<Response<BaseResponse<ListBodyEntity<ShelterEntity>>>>

    suspend fun getRescuedAnimal(
        animalSearchFilter: AnimalSearchFilterEntity
    ): Flow<Response<BaseResponse<ListBodyEntity<AnimalEntity>>>>
}