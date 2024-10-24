package com.example.rescuedanimals.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rescuedanimals.data.model.local.AnimalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAnimalDao {

    // 생성
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteAnimal(favoriteAnimal: AnimalEntity): Flow<Long>

    // 삭제
    @Query("DELETE FROM animal where desertionNo = :desertionNo")
    fun deleteFavoriteAnimal(desertionNo: Long): Flow<Long>

    @Query("DELETE FROM animal")
    fun deleteAll(): Flow<Long>

//    // 업데이트
//    @Query("UPDATE sido SET sido_title = :title WHERE id = :id")
//    fun updateTitle(id: Long, title: String)
//
//    @Query("UPDATE sido SET sido_content = :content WHERE id = :id")
//    fun updateContent(id: Long, content: String)
//
//    @Query("UPDATE sido SET sido_date = :date WHERE id = :id")
//    fun updateDate(id: Long, date: LocalDateTime)

    // 탐색
    @Query("SELECT * FROM animal")
    fun getAll(): Flow<List<AnimalEntity>>

    @Query("SELECT * FROM animal where desertionNo = :desertionNo")
    fun getFavoriteAnimal(desertionNo: Long): Flow<AnimalEntity>
}