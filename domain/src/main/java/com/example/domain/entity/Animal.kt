package com.example.domain.entity

import kotlinx.serialization.Serializable

//import androidx.annotation.Keep

//@Keep
//@JsonClass(generateAdapter = true)

@Serializable
data class Animal(
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
)