package com.example.data.datasource.local

import com.example.data.model.local.DBAnimalEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteAnimalDataSource {
    suspend fun getAll(): Flow<List<DBAnimalEntity>>

    suspend fun getFavoriteAnimal(desertionNo: String): Flow<DBAnimalEntity>

    suspend fun insertFavoriteAnimal(favoriteAnimal: DBAnimalEntity): Flow<Long>

    suspend fun deleteFavoriteAnimal(desertionNo: String): Flow<Long>

    suspend fun deleteAll(): Flow<Long>
}