package com.example.rescuedanimals.data.mapper

import com.example.rescuedanimals.data.model.local.DBAnimalEntity
import com.example.rescuedanimals.domain.entity.Animal


object AnimalMapper {
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
        )

    fun mapperToAnimalList(dbAnimalEntity: List<DBAnimalEntity>): List<Animal> {
        val animals = mutableListOf<Animal>()

        dbAnimalEntity.forEach { entity ->
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
        )

    fun mapperToAnimalEntityList(animalEntity: List<Animal>): List<DBAnimalEntity> {
        val animals = mutableListOf<DBAnimalEntity>()

        animalEntity.forEach { entity ->
            animals.add(mapperToAnimalEntity(entity))
        }
        return animals.toList()
    }
}