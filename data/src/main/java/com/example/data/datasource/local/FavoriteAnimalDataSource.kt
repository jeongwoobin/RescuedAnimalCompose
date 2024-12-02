package com.example.data.datasource.local

import com.example.data.model.local.DBAnimalEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteAnimalDataSource {
    suspend fun selectAll(): List<DBAnimalEntity>

    suspend fun selectFavoriteAnimal(desertionNo: Long): Flow<DBAnimalEntity>

    suspend fun insertFavoriteAnimal(favoriteAnimal: DBAnimalEntity): Flow<Long>

    suspend fun deleteFavoriteAnimal(desertionNo: Long): Flow<Int>

    suspend fun deleteAll(): Flow<Int>
}