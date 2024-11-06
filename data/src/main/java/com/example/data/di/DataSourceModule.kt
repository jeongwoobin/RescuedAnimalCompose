package com.example.data.di

import com.example.data.database.FavoriteAnimalDao
import com.example.data.datasource.local.FavoriteAnimalDataSource
import com.example.data.datasource.local.FavoriteAnimalDataSourceImpl
import com.example.data.service.RescuedAnimalsApi
import com.example.data.datasource.remote.RescuedAnimalsDataSource
import com.example.data.datasource.remote.RescuedAnimalsDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun provideRescuedAnimalRemoteDataSource(rescuedAnimalsApi: RescuedAnimalsApi): RescuedAnimalsDataSource {
        return RescuedAnimalsDataSourceImpl(rescuedAnimalsApi)
    }

    @Provides
    @Singleton
    fun provideFavoriteAnimalRemoteDataSource(favoriteAnimalDao: FavoriteAnimalDao): FavoriteAnimalDataSource {
        return FavoriteAnimalDataSourceImpl(favoriteAnimalDao)
    }
}