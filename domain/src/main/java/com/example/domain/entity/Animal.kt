package com.example.domain.entity

//import androidx.annotation.Keep

//@Keep
//@JsonClass(generateAdapter = true)
data class Animal(
    val desertionNo: String,
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
    var favorite: Boolean?
)