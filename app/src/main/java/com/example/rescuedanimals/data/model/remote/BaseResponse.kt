package com.example.rescuedanimals.data.model.remote

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    @Json(name="response")
    val response: Response<T>,
)

@Keep
@JsonClass(generateAdapter = true)
data class Response<T>(
    @Json(name="header")
    val header: Header,
    @Json(name="body")
    val body: T?,
)

@Keep
@JsonClass(generateAdapter = true)
data class Header(
    @Json(name="reqNo")
    val reqNo: Long,
    @Json(name="resultCode")
    val resultCode: String,
    @Json(name="resultMsg")
    val resultMsg: String,
)

@Keep
@JsonClass(generateAdapter = true)
data class ListBody<T>(
    @Json(name="items")
    val items: Item<T>,
    @Json(name="numOfRows")
    val numOfRows: Long,
    @Json(name="pageNo")
    val pageNo: Long,
    @Json(name="totalCount")
    val totalCount: Long,
)

@Keep
@JsonClass(generateAdapter = true)
data class Item<T>(
    @Json(name="item")
    val item: List<T>
)

@Keep
@JsonClass(generateAdapter = true)
data class Body<T>(
    @Json(name="item")
    val item: T
)
