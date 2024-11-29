package com.example.data.mapper

import com.example.data.model.local.DBAnimalEntity
import com.example.domain.entity.Animal

object DBAnimalMapper {

    fun mapperToAnimal(entity: DBAnimalEntity): Animal =
        Animal(
            desertionNo = entity.desertionNo,
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

    fun mapperToAnimalList(animalEntity: List<DBAnimalEntity>): List<Animal> {
        val animals = mutableListOf<Animal>()

        animalEntity.forEach { entity ->
            animals.add(mapperToAnimal(entity))
        }
        return animals.toList()
    }

    fun mapperToAnimalEntity(entity: Animal): DBAnimalEntity =
        DBAnimalEntity(
            desertionNo = entity.desertionNo,
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

    fun mapperToAnimalEntityList(animalEntity: List<Animal>): List<DBAnimalEntity> {
        val animals = mutableListOf<DBAnimalEntity>()

        animalEntity.forEach { entity ->
            animals.add(mapperToAnimalEntity(entity))
        }
        return animals.toList()
    }
}