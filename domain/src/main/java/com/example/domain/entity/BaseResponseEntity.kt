package com.example.domain.entity


data class ListBodyEntity<T>(
    val items: ItemEntity<T>,
    val numOfRows: Long,
    val pageNo: Long,
    val totalCount: Long,
)

data class ItemEntity<T>(
    val item: List<T>
)