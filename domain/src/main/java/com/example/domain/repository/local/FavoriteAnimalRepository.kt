package com.example.domain.repository.local

import com.example.domain.entity.Animal
import com.example.domain.entity.Result
import kotlinx.coroutines.flow.Flow

interface FavoriteAnimalRepository {

    suspend fun selectAll(): Flow<Result<List<Animal>>>

    suspend fun selectFavoriteAnimal(desertionNo: String): Flow<Result<Animal>>

    suspend fun insertFavoriteAnimal(favoriteAnimal: Animal): Flow<Result<Boolean>>

    suspend fun deleteFavoriteAnimal(desertionNo: String): Flow<Result<Boolean>>

    suspend fun deleteAll(): Flow<Result<Boolean>>
}
