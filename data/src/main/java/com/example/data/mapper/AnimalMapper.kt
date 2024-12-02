package com.example.data.mapper

import com.example.data.model.remote.AnimalEntity
import com.example.domain.entity.Animal
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object AnimalMapper {

    fun mapperToAnimal(entity: AnimalEntity): Animal =
        Animal(
            desertionNo = entity.desertionNo.toLong(), //toLongOrNull() ?: -1L,
            filename = entity.filename,
            happenDt = entity.happenDt,
            happenPlace = entity.happenPlace,
            kindCd = entity.kindCd,
            colorCd = entity.colorCd,
            age = entity.age,
            weight = entity.weight,
            noticeNo = entity.noticeNo,
            noticeSdt = entity.noticeSdt,
            noticeEdt = entity.noticeEdt,
            popfile = entity.popfile,
            processState = entity.processState,
            sexCd = entity.sexCd,
            neuterYn = entity.neuterYn,
            specialMark = entity.specialMark,
            careNm = entity.careNm,
            careTel = entity.careTel,
            careAddr = entity.careAddr,
            orgNm = entity.orgNm,
            chargeNm = entity.chargeNm,
            officetel = entity.officetel,
            favorite = entity.favorite
        )

    fun mapperToAnimalList(animalEntity: List<AnimalEntity>): List<Animal> {
        val animals = mutableListOf<Animal>()

        animalEntity.forEach { entity ->
            animals.add(mapperToAnimal(entity))
        }
        return animals.toList()
    }

    fun mapperToAnimalEntity(entity: Animal): AnimalEntity =
        AnimalEntity(
            desertionNo = entity.desertionNo.toString(),
            filename = entity.filename,
            happenDt = entity.happenDt,
            happenPlace = entity.happenPlace,
            kindCd = entity.kindCd,
            colorCd = entity.colorCd,
            age = entity.age,
            weight = entity.weight,
            noticeNo = entity.noticeNo,
            noticeSdt = entity.noticeSdt,
            noticeEdt = entity.noticeEdt,
            popfile = entity.popfile,
            processState = entity.processState,
            sexCd = entity.sexCd,
            neuterYn = entity.neuterYn,
            specialMark = entity.specialMark,
            careNm = entity.careNm,
            careTel = entity.careTel,
            careAddr = entity.careAddr,
            orgNm = entity.orgNm,
            chargeNm = entity.chargeNm,
            officetel = entity.officetel,
            favorite = entity.favorite
        )

    fun mapperToAnimalEntityList(animalEntity: List<Animal>): List<AnimalEntity> {
        val animals = mutableListOf<AnimalEntity>()

        animalEntity.forEach { entity ->
            animals.add(mapperToAnimalEntity(entity))
        }
        return animals.toList()
    }
}