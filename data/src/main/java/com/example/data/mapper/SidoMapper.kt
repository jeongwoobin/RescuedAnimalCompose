package com.example.data.mapper

import com.example.data.model.remote.SidoEntity
import com.example.rescuedanimals.domain.entity.Sido

object SidoMapper {
    operator fun invoke(sidoEntity: List<SidoEntity>): List<Sido> {
        val sidos = mutableListOf<Sido>()

        sidoEntity.forEach { entity ->
            sidos.add(Sido(entity.orgCd, entity.orgdownNm))
        }
        return sidos.toList()
    }
}