package com.example.data.repository.local

import com.example.data.datasource.local.FavoriteAnimalDataSource
import com.example.data.mapper.AnimalMapper
import com.example.domain.entity.Animal
import com.example.domain.repository.local.FavoriteAnimalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.example.domain.entity.Result

class FavoriteAnimalRepositoryImpl @Inject constructor(
    private val favoriteAnimalDataSource: FavoriteAnimalDataSource
) : FavoriteAnimalRepository {
    override suspend fun getAll(): Flow<Result<List<Animal>>> = flow {
        favoriteAnimalDataSource.getAll().collect { value ->
            emit(Result.success(AnimalMapper.mapperToAnimalList(value)))
        }
    }

    override suspend fun getFavoriteAnimal(desertionNo: String): Flow<Result<Animal>> = flow {
        favoriteAnimalDataSource.getFavoriteAnimal(desertionNo = desertionNo)
            .collect { value -> emit( Result.success(AnimalMapper.mapperToAnimal(value))) }
    }

    override suspend fun insertFavoriteAnimal(favoriteAnimal: Animal): Flow<Result<Boolean>> =
        flow {
            favoriteAnimalDataSource.insertFavoriteAnimal(
                favoriteAnimal = AnimalMapper.mapperToAnimalEntity(
                    favoriteAnimal
                )
            )
                .collect { value -> if (value != 0L) emit(Result.success(true)) else emit(Result.fail()) }
        }

    override suspend fun deleteFavoriteAnimal(desertionNo: String): Flow<Result<Boolean>> =
        flow {
            favoriteAnimalDataSource.deleteFavoriteAnimal(desertionNo = desertionNo)
                .collect { value -> if (value != 0L) emit(Result.success(true)) else emit(Result.fail()) }
        }

    override suspend fun deleteAll(): Flow<Result<Boolean>> =
        flow {
            favoriteAnimalDataSource.deleteAll()
                .collect { value -> if (value != 0L) emit(Result.success(true)) else emit(Result.fail()) }
        }
}