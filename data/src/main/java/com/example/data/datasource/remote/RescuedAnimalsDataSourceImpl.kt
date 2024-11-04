package com.example.data.datasource.remote

import com.example.data.service.RescuedAnimalsApi
import com.example.data.model.remote.BaseResponse
import com.example.data.model.remote.ListBody
import com.example.data.model.remote.SidoEntity
import retrofit2.Response
import javax.inject.Inject

class RescuedAnimalsDataSourceImpl @Inject constructor(
    private val api: RescuedAnimalsApi
) : RescuedAnimalsDataSource {

    override suspend fun getSido(): Response<BaseResponse<ListBody<SidoEntity>>> = api.fetchSido()

}