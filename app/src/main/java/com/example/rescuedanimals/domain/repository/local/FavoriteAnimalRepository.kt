package com.example.rescuedanimals.domain.repository.local

import com.example.rescuedanimals.data.model.local.AnimalEntity
import kotlinx.coroutines.flow.Flow
import com.example.rescuedanimals.domain.entity.Result

interface FavoriteAnimalRepository {

    suspend fun getAll(): Flow<Result<List<AnimalEntity>>>

    suspend fun getFavoriteAnimal(desertionNo: Long): Flow<Result<AnimalEntity>>

    suspend fun insertFavoriteAnimal(favoriteAnimal: AnimalEntity): Flow<Result<Boolean>>

    suspend fun deleteFavoriteAnimal(desertionNo: Long): Flow<Result<Boolean>>

    suspend fun deleteAll(): Flow<Result<Boolean>>
}
