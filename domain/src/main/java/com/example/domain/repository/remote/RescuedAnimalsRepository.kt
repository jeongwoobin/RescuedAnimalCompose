package com.example.domain.repository.remote

import com.example.domain.entity.Animal
import com.example.domain.entity.AnimalSearchFilter
import com.example.domain.entity.ListBodyEntity
import com.example.domain.entity.Result
import com.example.domain.entity.Shelter
import com.example.domain.entity.Sido
import com.example.domain.entity.Sigungu
import kotlinx.coroutines.flow.Flow

interface RescuedAnimalsRepository {
    suspend fun getSido(): Flow<Result<List<Sido>>>

    suspend fun getSigungu(upr_cd: String): Flow<Result<List<Sigungu>>>

    suspend fun getShelter(upr_cd: String, org_cd: String): Flow<Result<List<Shelter>>>

    suspend fun getRescuedAnimal(
        animalSearchFilter: AnimalSearchFilter
    ): Flow<Result<ListBodyEntity<Animal>>>
}