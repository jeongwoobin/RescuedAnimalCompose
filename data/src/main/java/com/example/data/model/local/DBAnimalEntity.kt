package com.example.data.model.local

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "animal")
data class DBAnimalEntity(
    val desertionNo: Long,
    val filename: String?,
    val happenDt: String?,
    val happenPlace: String?,
    val kindCd: String?,
    val colorCd: String?,
    val age: String?,
    val weight: String?,
    val noticeNo: String?,
    val noticeSdt: String?,
    val noticeEdt: String?,
    val popfile: String?,
    val processState: String?,
    val sexCd: String?,
    val neuterYn: String?,
    val specialMark: String?,
    val careNm: String?,
    val careTel: String?,
    val careAddr: String?,
    val orgNm: String?,
    val chargeNm: String?,
    val officetel: String?,
    val favorite: Boolean?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
