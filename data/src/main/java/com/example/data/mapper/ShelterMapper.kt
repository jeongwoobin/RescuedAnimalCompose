package com.example.data.mapper

import com.example.data.model.remote.ShelterEntity
import com.example.data.model.remote.SidoEntity
import com.example.data.model.remote.SigunguEntity
import com.example.domain.entity.Shelter
import com.example.domain.entity.Sido
import com.example.domain.entity.Sigungu

object ShelterMapper {
    operator fun invoke(shelterEntity: List<ShelterEntity>): List<Shelter> {
        val shelter = mutableListOf<Shelter>()

        shelterEntity.forEach { entity ->
            shelter.add(Shelter(entity.careRegNo, entity.careNm))
        }
        return shelter.toList()
    }
}