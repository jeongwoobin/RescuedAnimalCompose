package com.example.data.model.remote

import androidx.annotation.Keep
import com.example.domain.entity.Neuter
import com.example.domain.entity.Shelter
import com.example.domain.entity.Sido
import com.example.domain.entity.Sigungu
import com.example.domain.entity.State
import com.example.domain.entity.Upkind
import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable


@JsonClass(generateAdapter = true)
data class SidoEntity(
    val orgCd: String, val orgdownNm: String
)


@JsonClass(generateAdapter = true)
data class SigunguEntity(
    val uprCd: String, val orgCd: String, val orgdownNm: String
)


@JsonClass(generateAdapter = true)
data class ShelterEntity(
    val careRegNo: String, val careNm: String
)


@JsonClass(generateAdapter = true)
data class AnimalEntity(
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
    val favorite: Boolean?
)



@JsonClass(generateAdapter = true)
data class AnimalSearchFilterEntity(
    var upr_cd: Sido? = null,
    var org_cd: Sigungu? = null,
    var care_reg_no: Shelter? = null,
    var bgnde: String? = null,
    var endde: String? = null,
    var upkind: Upkind? = null ?: Upkind.ALL,
    var neuter: Neuter? = null ?: Neuter.ALL,
    var state: State? = null ?: State.ALL,
    var pageNo: Int = 1,
) {
    val numOfRows: Int = if (pageNo != 1) 20 else 40
}