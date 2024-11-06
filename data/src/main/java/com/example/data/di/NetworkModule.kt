package com.example.data.di

import com.example.data.BuildConfig
import com.example.data.util.AuthInterceptor
import com.example.data.util.PublicSrvcParamInterceptor
import com.orhanobut.logger.Logger
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        return MoshiConverterFactory.create(moshi)
    }


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Logger.t("Okhttp").i(message)
        }.setLevel(level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor("")
    }

    @Provides
    @Singleton
    fun providePublicSrvcParamInterceptor(): PublicSrvcParamInterceptor {
        return PublicSrvcParamInterceptor
    }

    // Retrofit
    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        publicSrvcParamInterceptor: PublicSrvcParamInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(authInterceptor)
            addInterceptor(publicSrvcParamInterceptor)
        }.build()
    }

    @Singleton
    @Provides
    @Named("RescuedAnimals")
    fun provideRescuedAnimalsRetrofit(
        okHttpClient: OkHttpClient, moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.RESCUED_ANIMALS_BASE_URL)
            .addConverterFactory(moshiConverterFactory).client(okHttpClient).build()
    }

    @Singleton
    @Provides
    @Named("AnimalInfo")
    fun provideAnimalInfoRetrofit(
        okHttpClient: OkHttpClient, moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.ANIMAL_INFO_BASE_URL)
            .addConverterFactory(moshiConverterFactory).client(okHttpClient).build()
    }
}