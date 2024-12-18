package com.example.domain.entity


data class ListBody<T>(
    val items: ListItem<T>,
    val numOfRows: Long?,
    val pageNo: Long?,
    val totalCount: Long?,
)

data class ListItem<T>(
    val item: List<T>?
)

data class Item<T>(
    val item: T
)