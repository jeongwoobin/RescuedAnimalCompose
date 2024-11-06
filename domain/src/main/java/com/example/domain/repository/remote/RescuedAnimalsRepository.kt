package com.example.domain.repository.remote

import com.example.domain.entity.Animal
import com.example.domain.entity.ListBodyEntity
import com.example.domain.entity.Result
import com.example.domain.entity.Sido
import kotlinx.coroutines.flow.Flow

interface RescuedAnimalsRepository {
    suspend fun getSido(): Flow<Result<List<Sido>>>

    suspend fun getRescuedAnimal(
        pageNo: Int,
        numOfRows: Int): Flow<Result<ListBodyEntity<Animal>>>
}