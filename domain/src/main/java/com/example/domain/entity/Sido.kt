package com.example.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Sido(
    val orgCd: String,
    val orgdownNm: String
)