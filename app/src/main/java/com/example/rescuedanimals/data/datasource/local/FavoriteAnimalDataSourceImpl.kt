package com.example.rescuedanimals.data.datasource.local

import com.example.rescuedanimals.data.database.FavoriteAnimalDao
import com.example.rescuedanimals.data.model.local.AnimalEntity
import kotlinx.coroutines.flow.Flow


class FavoriteAnimalDataSourceImpl(private val favoriteAnimalDao: FavoriteAnimalDao): FavoriteAnimalDataSource {
    override suspend fun getAll(): Flow<List<AnimalEntity>> = favoriteAnimalDao.getAll()

    override suspend fun getFavoriteAnimal(desertionNo: Long): Flow<AnimalEntity> = favoriteAnimalDao.getFavoriteAnimal(desertionNo = desertionNo)

    override suspend fun insertFavoriteAnimal(favoriteAnimal: AnimalEntity): Flow<Long> = favoriteAnimalDao.insertFavoriteAnimal(favoriteAnimal = favoriteAnimal)

    override suspend fun deleteFavoriteAnimal(desertionNo: Long): Flow<Long> = favoriteAnimalDao.deleteFavoriteAnimal(desertionNo = desertionNo)

    override suspend fun deleteAll(): Flow<Long> = favoriteAnimalDao.deleteAll()
}