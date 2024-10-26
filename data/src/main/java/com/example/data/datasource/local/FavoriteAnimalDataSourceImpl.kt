package com.example.data.datasource.local

import com.example.data.database.FavoriteAnimalDao
import com.example.data.model.local.DBAnimalEntity
import kotlinx.coroutines.flow.Flow


class FavoriteAnimalDataSourceImpl(private val favoriteAnimalDao: FavoriteAnimalDao):
    FavoriteAnimalDataSource {
    override suspend fun getAll(): Flow<List<DBAnimalEntity>> = favoriteAnimalDao.getAll()

    override suspend fun getFavoriteAnimal(desertionNo: String): Flow<DBAnimalEntity> = favoriteAnimalDao.getFavoriteAnimal(desertionNo = desertionNo)

    override suspend fun insertFavoriteAnimal(favoriteAnimal: DBAnimalEntity): Flow<Long> = favoriteAnimalDao.insertFavoriteAnimal(favoriteAnimal = favoriteAnimal)

    override suspend fun deleteFavoriteAnimal(desertionNo: String): Flow<Long> = favoriteAnimalDao.deleteFavoriteAnimal(desertionNo = desertionNo)

    override suspend fun deleteAll(): Flow<Long> = favoriteAnimalDao.deleteAll()
}