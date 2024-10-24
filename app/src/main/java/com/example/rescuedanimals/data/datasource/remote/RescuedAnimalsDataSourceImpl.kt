package com.example.rescuedanimals.data.datasource.remote

import com.example.rescuedanimals.data.service.RescuedAnimalsApi
import com.example.rescuedanimals.data.model.remote.BaseResponse
import com.example.rescuedanimals.data.model.remote.ListBody
import com.example.rescuedanimals.data.model.remote.SidoEntity
import retrofit2.Response
import javax.inject.Inject

class RescuedAnimalsDataSourceImpl @Inject constructor(
    private val api: RescuedAnimalsApi
) : RescuedAnimalsDataSource {

    override suspend fun getSido(): Response<BaseResponse<ListBody<SidoEntity>>> = api.fetchSido()

}