package com.example.rescuedanimals.data.di

import android.content.Context
import androidx.room.Room
import com.example.rescuedanimals.data.database.FavoriteAnimalDao
import com.example.rescuedanimals.data.database.FavoriteAnimalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideFavoriteAnimalDatabase(@ApplicationContext appContext: Context) : FavoriteAnimalDatabase {
        return Room.databaseBuilder(
            appContext,
            FavoriteAnimalDatabase::class.java,
            FavoriteAnimalDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun providesDao(favoriteAnimalDatabase: FavoriteAnimalDatabase) : FavoriteAnimalDao {
        return favoriteAnimalDatabase.getFavoriteAnimalDao()
    }

}