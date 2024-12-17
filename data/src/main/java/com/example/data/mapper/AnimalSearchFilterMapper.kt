package com.example.data.mapper

import com.example.data.model.remote.AnimalSearchFilterEntity
import com.example.domain.entity.AnimalSearchFilter

object AnimalSearchFilterMapper {
    operator fun invoke(animalSearchFilter: AnimalSearchFilter): AnimalSearchFilterEntity =
        AnimalSearchFilterEntity(
            upr_cd = animalSearchFilter.upr_cd,
            org_cd = animalSearchFilter.org_cd,
            care_reg_no = animalSearchFilter.care_reg_no,
            bgnde = animalSearchFilter.bgnde,
            endde = animalSearchFilter.endde,
            upkind = animalSearchFilter.upkind,
            neuter = animalSearchFilter.neuter,
            state = animalSearchFilter.state,
            pageNo = animalSearchFilter.pageNo
        )
}