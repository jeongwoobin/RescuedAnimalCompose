package com.example.data.datasource.remote

import com.example.data.model.remote.AnimalEntity
import com.example.data.model.remote.BaseResponse
import com.example.data.model.remote.ListBody
import com.example.data.model.remote.SidoEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RescuedAnimalsDataSource {
    suspend fun getSido(): Flow<Response<BaseResponse<ListBody<SidoEntity>>>>

    suspend fun getRescuedAnimal(
        pageNo: Int,
        numOfRows: Int
    ): Flow<Response<BaseResponse<ListBody<AnimalEntity>>>>
}