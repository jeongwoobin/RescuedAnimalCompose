package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.model.remote.AnimalEntity
import com.example.data.util.ListConverter

@Database(entities = [AnimalEntity::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class FavoriteAnimalDatabase : RoomDatabase() {
    abstract fun favoriteAnimalDao(): FavoriteAnimalDao

    companion object {
        const val DATABASE_NAME = "favorite_animal.db"
    }
}