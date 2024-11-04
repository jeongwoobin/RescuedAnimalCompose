package com.example.data.di

import com.example.data.datasource.remote.RescuedAnimalsDataSource
import com.example.data.repository.remote.RescuedAnimalsRepositoryImpl
import com.example.rescuedanimals.domain.repository.remote.RescuedAnimalsRepository
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

//    @Provides
//    @Singleton
//    fun provideAnimalInfoRepository(dataStore: DataStore<Preferences>): SettingsRepository {
//        return SettingsRepositoryImpl(dataStore)
//    }
}