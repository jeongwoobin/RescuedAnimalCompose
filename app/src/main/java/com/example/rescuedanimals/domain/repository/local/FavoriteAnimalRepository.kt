package com.example.rescuedanimals.domain.repository.local

import com.example.rescuedanimals.domain.entity.Animal
import kotlinx.coroutines.flow.Flow
import com.example.rescuedanimals.domain.entity.Result

interface FavoriteAnimalRepository {

    suspend fun getAll(): Flow<Result<List<Animal>>>

    suspend fun getFavoriteAnimal(desertionNo: String): Flow<Result<Animal>>

    suspend fun insertFavoriteAnimal(favoriteAnimal: Animal): Flow<Result<Boolean>>

    suspend fun deleteFavoriteAnimal(desertionNo: String): Flow<Result<Boolean>>

    suspend fun deleteAll(): Flow<Result<Boolean>>
}
