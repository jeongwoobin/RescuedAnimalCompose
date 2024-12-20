package com.example.data.model.remote

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    @Json(name="response")
    val response: Response<T>,
)


@JsonClass(generateAdapter = true)
data class Response<T>(
    @Json(name="header")
    val header: Header,
    @Json(name="body")
    val body: T?,
)


@JsonClass(generateAdapter = true)
data class Header(
    @Json(name="reqNo")
    val reqNo: Long,
    @Json(name="resultCode")
    val resultCode: String,
    @Json(name="resultMsg")
    val resultMsg: String,
)


@JsonClass(generateAdapter = true)
data class ListBodyEntity<T>(
    @Json(name="items")
    val items: ListItemEntity<T>,
    @Json(name="numOfRows")
    val numOfRows: Long?,
    @Json(name="pageNo")
    val pageNo: Long?,
    @Json(name="totalCount")
    val totalCount: Long?,
)


@JsonClass(generateAdapter = true)
data class ListItemEntity<T>(
    @Json(name="item")
    val item: List<T>?
)


@JsonClass(generateAdapter = true)
data class ItemEntity<T>(
    @Json(name="item")
    val item: T
)
