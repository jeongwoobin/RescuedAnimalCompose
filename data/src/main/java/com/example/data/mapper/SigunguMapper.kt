package com.example.data.mapper

import com.example.data.model.remote.SidoEntity
import com.example.data.model.remote.SigunguEntity
import com.example.domain.entity.Sido
import com.example.domain.entity.Sigungu

object SigunguMapper {
    operator fun invoke(sigunguEntity: List<SigunguEntity>): List<Sigungu> {
        val sigungu = mutableListOf<Sigungu>()

        sigunguEntity.forEach { entity ->
            sigungu.add(Sigungu(entity.uprCd, entity.orgCd, entity.orgdownNm))
        }
        return sigungu.toList()
    }
}