package com.example.data.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.prefs.Preferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

//    private const val DATA_STORE_NAME = "data_store"
//
//    @Provides
//    @Singleton
//    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
//        return PreferenceDataStoreFactory.create(
//            produceFile = {
//                context.preferencesDataStoreFile(DATA_STORE_NAME)
//            }
//        )
//    }
}