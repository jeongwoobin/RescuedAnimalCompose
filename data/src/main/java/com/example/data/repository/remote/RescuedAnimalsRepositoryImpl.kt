package com.example.data.repository.remote

import com.example.data.datasource.remote.RescuedAnimalsDataSource
import com.example.data.mapper.SidoMapper
import com.example.domain.entity.Sido
import com.example.domain.repository.remote.RescuedAnimalsRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.domain.entity.Result
import com.orhanobut.logger.Logger


class RescuedAnimalsRepositoryImpl @Inject constructor(
    private val dataSource: RescuedAnimalsDataSource
) : RescuedAnimalsRepository {

    //        val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
//            dataSource.getSido()
//        }
//        Logger.d(response.body())
//        return Result.success(SidoEntity(item = listOf(SidoItemEntity(orgCd = "1", orgdownNm = "2"))))
//    }
    override suspend fun getSido(): Result<List<Sido>> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                dataSource.getSido()
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                if (body.response.body != null) {
                    Result.success(SidoMapper(body.response.body.items.item))
                } else {
                    Result.error(body.response.header.resultMsg, null)
                }
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }

}
