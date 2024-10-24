package com.example.rescuedanimals.data.repository.local

import com.example.rescuedanimals.data.datasource.local.FavoriteAnimalDataSource
import com.example.rescuedanimals.data.model.local.AnimalEntity
import com.example.rescuedanimals.domain.entity.Result
import com.example.rescuedanimals.domain.repository.local.FavoriteAnimalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteAnimalRepositoryImpl @Inject constructor(
    private val favoriteAnimalDataSource: FavoriteAnimalDataSource
) : FavoriteAnimalRepository {
    override suspend fun getAll(): Flow<Result<List<AnimalEntity>>> = flow {
        favoriteAnimalDataSource.getAll().collect { value -> emit(Result.success(value)) }
    }

    override suspend fun getFavoriteAnimal(desertionNo: Long): Flow<Result<AnimalEntity>> = flow {
        favoriteAnimalDataSource.getFavoriteAnimal(desertionNo = desertionNo)
            .collect { value -> emit(Result.success(value)) }
    }

    override suspend fun insertFavoriteAnimal(favoriteAnimal: AnimalEntity): Flow<Result<Boolean>> = flow {
        favoriteAnimalDataSource.insertFavoriteAnimal(favoriteAnimal = favoriteAnimal)
            .collect { value -> emit(Result.success(value)) }
    }

    override suspend fun deleteFavoriteAnimal(desertionNo: Long): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }
}