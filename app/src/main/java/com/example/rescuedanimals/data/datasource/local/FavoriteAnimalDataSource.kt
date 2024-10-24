package com.example.rescuedanimals.data.datasource.local

import com.example.rescuedanimals.data.model.local.AnimalEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteAnimalDataSource {
    suspend fun getAll(): Flow<List<AnimalEntity>>

    suspend fun getFavoriteAnimal(desertionNo: Long): Flow<AnimalEntity>

    suspend fun insertFavoriteAnimal(favoriteAnimal: AnimalEntity): Flow<Long>

    suspend fun deleteFavoriteAnimal(desertionNo: Long): Flow<Long>

    suspend fun deleteAll(): Flow<Long>
}