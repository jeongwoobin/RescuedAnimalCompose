package com.example.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Sido(
    val orgCd: String,
    val orgdownNm: String
)

@Serializable
data class Sigungu(
    val uprCd: String,
    val orgCd: String,
    val orgdownNm: String
)

@Serializable
data class Shelter(
    val careRegNo: String,
    val careNm: String
)