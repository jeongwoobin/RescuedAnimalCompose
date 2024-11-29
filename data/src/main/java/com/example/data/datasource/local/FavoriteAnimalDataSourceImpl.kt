package com.example.data.datasource.local

import com.example.data.database.FavoriteAnimalDao
import com.example.data.model.local.DBAnimalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteAnimalDataSourceImpl @Inject constructor(private val favoriteAnimalDao: FavoriteAnimalDao): FavoriteAnimalDataSource {
    override suspend fun selectAll(): Flow<List<DBAnimalEntity>> = favoriteAnimalDao.selectAll()

    override suspend fun selectFavoriteAnimal(desertionNo: Long): Flow<DBAnimalEntity> = favoriteAnimalDao.selectFavoriteAnimal(desertionNo = desertionNo)

    override suspend fun insertFavoriteAnimal(favoriteAnimal: DBAnimalEntity): Flow<Long> = flow { emit(favoriteAnimalDao.insertFavoriteAnimal(favoriteAnimal = favoriteAnimal)) }

    override suspend fun deleteFavoriteAnimal(desertionNo: Long): Flow<Int> = flow { emit(favoriteAnimalDao.deleteFavoriteAnimal(desertionNo = desertionNo)) }

    override suspend fun deleteAll(): Flow<Int> = flow { emit(favoriteAnimalDao.deleteAll()) }
}