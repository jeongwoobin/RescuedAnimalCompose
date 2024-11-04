package com.example.data.di

import com.example.data.service.RescuedAnimalsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideRescuedAnimalApiService(@Named("RescuedAnimals") retrofit: Retrofit): RescuedAnimalsApi {
        return retrofit.create(RescuedAnimalsApi::class.java)
    }
}