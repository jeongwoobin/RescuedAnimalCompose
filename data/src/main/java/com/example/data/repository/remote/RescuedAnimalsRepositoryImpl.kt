package com.example.data.repository.remote

import com.example.data.datasource.remote.RescuedAnimalsDataSource
import com.example.data.mapper.AnimalMapper
import com.example.data.mapper.ListBodyMapper
import com.example.data.mapper.SidoMapper
import com.example.data.model.remote.ListBody
import com.example.data.util.retryWhen
import com.example.domain.entity.Animal
import com.example.domain.entity.ListBodyEntity
import com.example.domain.entity.Sido
import com.example.domain.repository.remote.RescuedAnimalsRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.domain.entity.Result
import com.orhanobut.logger.Logger
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.net.UnknownServiceException


class RescuedAnimalsRepositoryImpl @Inject constructor(
    private val dataSource: RescuedAnimalsDataSource
) : RescuedAnimalsRepository {

    override suspend fun getSido(): Flow<Result<List<Sido>>> =
        dataSource.getSido().retryWhen { cause, retryCount, delayTime ->
            if (retryCount < 5) {
                Logger.e("retry cause: $cause, retryCount: $retryCount, delayTime: $delayTime")
                delay(delayTime)
                true
            } else false
        }.map { response ->
            Logger.d("sido response")
            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                if (body.response.body != null) {
                    val data = body.response.body
                    Result.success(
                        data = SidoMapper(data.items.item)
                    )
                } else {
                    Result.error(message = body.response.header.resultMsg)
                }
            } else {
                Result.error(message = response.errorBody().toString())
            }
        }.catch { e ->
            Logger.t(e.javaClass.typeName).e(e.message.toString())
            when (e) {
                is JsonDataException -> {
                    emit(Result.fail(message = "데이터 에러가 발생했습니다."))
                }

//            is NoNetworkException -> {
//                emit(Result.fail(message = e.message))
//            }

                is UnknownServiceException -> {
                    emit(Result.fail(message = "안전하지 않은 네트워크 연결 입니다.(HTTP)"))
                }

                else -> emit(Result.fail(message = "잠시 후 다시 시도해주세요."))
            }
        }

    override suspend fun getRescuedAnimal(
        pageNo: Int, numOfRows: Int
    ): Flow<Result<ListBodyEntity<Animal>>> = dataSource.getRescuedAnimal(
        pageNo = pageNo, numOfRows = numOfRows
    )
        .retryWhen { cause, retryCount, delayTime ->
            if (retryCount < 5) {
                Logger.e("retry cause: $cause, retryCount: $retryCount, delayTime: $delayTime")
                delay(delayTime)
                true
            } else false
        }.map { response ->
            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                if (body.response.body != null) {
                    val data = body.response.body
                    Result.success(
                        data = ListBodyMapper(
                            originEntity = data, newEntity = AnimalMapper.mapperToAnimalList(
                                data.items.item
                            )
                        )
                    )
                } else {
                    Result.error(message = body.response.header.resultMsg)
                }
            } else {
                Result.error(message = response.errorBody().toString())
            }
        }.catch { e ->
            Logger.t(e.javaClass.typeName).e(e.message.toString())
            when (e) {
                is JsonDataException -> {
                    emit(Result.fail(message = "데이터 에러가 발생했습니다."))
                }

//            is NoNetworkException -> {
//                emit(Result.fail(message = e.message))
//            }

                is UnknownServiceException -> {
                    emit(Result.fail(message = "안전하지 않은 네트워크 연결 입니다.(HTTP)"))
                }

                else -> {
                    emit(Result.fail(message = "잠시 후 다시 시도해주세요."))
                }
            }
        }
}
