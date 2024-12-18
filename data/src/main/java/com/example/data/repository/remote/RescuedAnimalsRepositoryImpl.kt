package com.example.data.repository.remote

import com.example.data.datasource.remote.RescuedAnimalsDataSource
import com.example.data.mapper.AnimalMapper
import com.example.data.mapper.AnimalSearchFilterMapper
import com.example.data.mapper.ListBodyMapper
import com.example.data.mapper.ShelterMapper
import com.example.data.mapper.SidoMapper
import com.example.data.mapper.SigunguMapper
import com.example.data.util.retryWhen
import com.example.domain.entity.Animal
import com.example.domain.entity.AnimalSearchFilter
import com.example.domain.entity.ListBody
import com.example.domain.entity.Sido
import com.example.domain.repository.remote.RescuedAnimalsRepository
import javax.inject.Inject
import com.example.domain.entity.Result
import com.example.domain.entity.Shelter
import com.example.domain.entity.Sigungu
import com.orhanobut.logger.Logger
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.net.UnknownServiceException


class RescuedAnimalsRepositoryImpl @Inject constructor(
    private val dataSource: RescuedAnimalsDataSource
) : RescuedAnimalsRepository {

    override suspend fun getSido(): Flow<Result<List<Sido>>> =
        dataSource.getSido().retryWhen { cause, retryCount, delayTime ->
            if (retryCount < 30) {
                Logger.e("retry cause: $cause, retryCount: $retryCount, delayTime: $delayTime")
                delay(delayTime)
                true
            } else false
        }.map { response ->
            Logger.d("getSido response")
            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                if (body.response.body != null) {
                    val data = body.response.body
                    Result.success(data = data.items.item?.let { SidoMapper(it) })
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

    override suspend fun getSigungu(upr_cd: String): Flow<Result<List<Sigungu>>> =
        dataSource.getSigungu(upr_cd = upr_cd).retryWhen { cause, retryCount, delayTime ->
            if (retryCount < 5) {
                Logger.e("retry cause: $cause, retryCount: $retryCount, delayTime: $delayTime")
                delay(delayTime)
                true
            } else false
        }.map { response ->
            Logger.d("getSigungu response")
            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                if (body.response.body != null) {
                    val data = body.response.body
                    Result.success(data = data.items.item?.let { SigunguMapper(it) })
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

    override suspend fun getShelter(upr_cd: String, org_cd: String): Flow<Result<List<Shelter>>> =
        dataSource.getShelter(upr_cd = upr_cd, org_cd = org_cd)
            .retryWhen { cause, retryCount, delayTime ->
                if (retryCount < 5) {
                    Logger.e("retry cause: $cause, retryCount: $retryCount, delayTime: $delayTime")
                    delay(delayTime)
                    true
                } else false
            }.map { response ->
                Logger.d("getShelter response")
                val body = response.body()
                if (response.isSuccessful && (body != null)) {
                    if (body.response.body != null) {
                        val data = body.response.body
                        Result.success(data = data.items.item?.let { ShelterMapper(it) })
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
        animalSearchFilter: AnimalSearchFilter
    ): Flow<Result<ListBody<Animal>>> = dataSource.getRescuedAnimal(
        animalSearchFilter = AnimalSearchFilterMapper.invoke(animalSearchFilter)
    ).retryWhen { cause, retryCount, delayTime ->
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
                Result.success(data = ListBodyMapper(originEntity = data,
                    newEntity = data.items.item?.let {
                        AnimalMapper.mapperToAnimalList(
                            it
                        )
                    }))
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
