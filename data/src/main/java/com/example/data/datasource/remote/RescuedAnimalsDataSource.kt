package com.example.data.datasource.remote

import com.example.data.model.remote.BaseResponse
import com.example.data.model.remote.ListBody
import com.example.data.model.remote.SidoEntity
import retrofit2.Response

interface RescuedAnimalsDataSource {
    suspend fun getSido(): Response<BaseResponse<ListBody<SidoEntity>>>
}