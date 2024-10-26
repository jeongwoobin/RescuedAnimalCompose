package com.example.data.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.rescuedanimals.domain.entity.Animal
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

@ProvidedTypeConverter
class ListConverter@Inject constructor(
    private val moshi: Moshi
) {
    @TypeConverter
    fun listToJson(value: List<Animal>?): String {
        val adapter: JsonAdapter<List<Animal>> = moshi.adapter(
            Types.newParameterizedType(
            List::class.java,
                Animal::class.java
        ))
        return adapter.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Animal>? {
        val adapter: JsonAdapter<List<Animal>> = moshi.adapter(
            Types.newParameterizedType(
                List::class.java,
                Animal::class.java
            ))
        return adapter.fromJson(value)
    }

//    @TypeConverter
//    fun longToDate(value: String?): LocalDateTime? {
//        return value?.let { LocalDateTime.parse(it) }
//    }
//
//    @TypeConverter
//    fun dateToLong(date: LocalDateTime?): String? {
//        return date?.toString()
//    }
}