package com.example.data.datasource.local

import com.example.data.database.FavoriteAnimalDao
import com.example.data.model.remote.AnimalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteAnimalDataSourceImpl @Inject constructor(private val favoriteAnimalDao: FavoriteAnimalDao): FavoriteAnimalDataSource {
    override suspend fun selectAll(): Flow<List<AnimalEntity>> = favoriteAnimalDao.selectAll()

    override suspend fun selectFavoriteAnimal(desertionNo: String): Flow<AnimalEntity> = favoriteAnimalDao.selectFavoriteAnimal(desertionNo = desertionNo)

    override suspend fun insertFavoriteAnimal(favoriteAnimal: AnimalEntity): Flow<Long> = flow { emit(favoriteAnimalDao.insertFavoriteAnimal(favoriteAnimal = favoriteAnimal)) }

    override suspend fun deleteFavoriteAnimal(desertionNo: String): Flow<Int> = flow { emit(favoriteAnimalDao.deleteFavoriteAnimal(desertionNo = desertionNo)) }

    override suspend fun deleteAll(): Flow<Int> = flow { emit(favoriteAnimalDao.deleteAll()) }
}