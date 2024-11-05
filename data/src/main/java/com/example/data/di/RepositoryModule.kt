package com.example.data.di

import com.example.data.datasource.local.FavoriteAnimalDataSource
import com.example.data.datasource.remote.RescuedAnimalsDataSource
import com.example.data.repository.local.FavoriteAnimalRepositoryImpl
import com.example.data.repository.remote.RescuedAnimalsRepositoryImpl
import com.example.domain.repository.local.FavoriteAnimalRepository
import com.example.domain.repository.remote.RescuedAnimalsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRescuedAnimalsRepository(rescuedAnimalsRemoteDataSource: RescuedAnimalsDataSource): RescuedAnimalsRepository {
        return RescuedAnimalsRepositoryImpl(rescuedAnimalsRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideFavoriteAnimalsRepository(favoriteAnimalDataSource: FavoriteAnimalDataSource): FavoriteAnimalRepository {
        return FavoriteAnimalRepositoryImpl(favoriteAnimalDataSource)
    }

//    @Provides
//    @Singleton
//    fun provideAnimalInfoRepository(dataStore: DataStore<Preferences>): SettingsRepository {
//        return SettingsRepositoryImpl(dataStore)
//    }
}