package com.example.data.service

import com.example.data.model.remote.BaseResponse
import com.example.data.model.remote.ListBody
import com.example.data.model.remote.SidoEntity
import retrofit2.Response
import retrofit2.http.GET

interface RescuedAnimalsApi {

    @GET("sido")
    suspend fun fetchSido(): Response<BaseResponse<ListBody<SidoEntity>>>
}