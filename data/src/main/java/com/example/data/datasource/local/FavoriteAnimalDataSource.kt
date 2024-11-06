package com.example.data.datasource.local

import com.example.data.model.remote.AnimalEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteAnimalDataSource {
    suspend fun selectAll(): Flow<List<AnimalEntity>>

    suspend fun selectFavoriteAnimal(desertionNo: String): Flow<AnimalEntity>

    suspend fun insertFavoriteAnimal(favoriteAnimal: AnimalEntity): Flow<Long>

    suspend fun deleteFavoriteAnimal(desertionNo: String): Flow<Int>

    suspend fun deleteAll(): Flow<Int>
}