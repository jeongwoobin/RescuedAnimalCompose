package com.example.data.mapper

import com.example.data.model.remote.ListBody
import com.example.domain.entity.ItemEntity
import com.example.domain.entity.ListBodyEntity

object ListBodyMapper {
    operator fun <T> invoke(originEntity: ListBody<*>, newEntity: List<T>): ListBodyEntity<T> {
        return ListBodyEntity(
            items = ItemEntity(newEntity),
            numOfRows = originEntity.numOfRows,
            pageNo = originEntity.pageNo,
            totalCount = originEntity.totalCount
        )
    }
}