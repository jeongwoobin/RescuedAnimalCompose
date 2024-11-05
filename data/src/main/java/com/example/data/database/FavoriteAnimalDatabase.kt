package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.util.ListConverter
import com.example.domain.entity.Animal

@Database(entities = [Animal::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class FavoriteAnimalDatabase : RoomDatabase() {
    abstract fun getFavoriteAnimalDao(): FavoriteAnimalDao

    companion object {
        const val DATABASE_NAME = "favorite_animal.db"
    }
}