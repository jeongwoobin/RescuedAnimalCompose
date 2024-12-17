package com.example.domain.entity


data class ListBody<T>(
    val items: Item<T>,
    val numOfRows: Long,
    val pageNo: Long,
    val totalCount: Long,
)

data class Item<T>(
    val item: List<T>
)