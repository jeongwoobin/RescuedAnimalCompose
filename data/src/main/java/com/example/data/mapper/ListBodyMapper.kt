package com.example.data.mapper

import com.example.data.model.remote.ListBodyEntity
import com.example.domain.entity.Item
import com.example.domain.entity.ListBody

object ListBodyMapper {
    operator fun <T> invoke(originEntity: ListBodyEntity<*>, newEntity: List<T>): ListBody<T> {
        return ListBody(
            items = Item(newEntity),
            numOfRows = originEntity.numOfRows,
            pageNo = originEntity.pageNo,
            totalCount = originEntity.totalCount
        )
    }
}