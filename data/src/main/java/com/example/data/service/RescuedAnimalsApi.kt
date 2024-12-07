package com.example.data.service

import com.example.data.model.remote.AnimalEntity
import com.example.data.model.remote.BaseResponse
import com.example.data.model.remote.ListBody
import com.example.data.model.remote.SidoEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RescuedAnimalsApi {

    @GET("sido")
    suspend fun fetchSido(): Response<BaseResponse<ListBody<SidoEntity>>>

    /**
     * upkind: 축종코드 (개 : 417000, 고양이 : 422400, 기타 : 429900)
     */
    @GET("abandonmentPublic")
    suspend fun fetchRescuedAnimal(
        @Query("upkind") upkind: Int?,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BaseResponse<ListBody<AnimalEntity>>>
}