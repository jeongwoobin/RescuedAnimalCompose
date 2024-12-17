package com.example.data.datasource.remote

import com.example.data.model.remote.AnimalEntity
import com.example.data.model.remote.AnimalSearchFilterEntity
import com.example.data.service.RescuedAnimalsApi
import com.example.data.model.remote.BaseResponse
import com.example.data.model.remote.ListBody
import com.example.data.model.remote.ShelterEntity
import com.example.data.model.remote.SidoEntity
import com.example.data.model.remote.SigunguEntity
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

    override suspend fun getSigungu(upr_cd: String): Flow<Response<BaseResponse<ListBody<SigunguEntity>>>> =
        flow { emit(api.fetchSigungu(upr_cd = upr_cd)) }

    override suspend fun getShelter(
        upr_cd: String, org_cd: String
    ): Flow<Response<BaseResponse<ListBody<ShelterEntity>>>> =
        flow { emit(api.fetchShelter(upr_cd = upr_cd, org_cd = org_cd)) }

    override suspend fun getRescuedAnimal(
        animalSearchFilter: AnimalSearchFilterEntity
    ): Flow<Response<BaseResponse<ListBody<AnimalEntity>>>> = flow {
        emit(
            api.fetchRescuedAnimal(
                upr_cd = animalSearchFilter.upr_cd?.orgCd,
                org_cd = animalSearchFilter.org_cd?.orgCd,
                care_reg_no = animalSearchFilter.care_reg_no?.careRegNo,
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