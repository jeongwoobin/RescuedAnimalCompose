package com.example.domain.usecase

import com.example.domain.entity.Result
import com.example.domain.entity.Shelter
import com.example.domain.entity.Sido
import com.example.domain.entity.Sigungu
import com.example.domain.repository.remote.RescuedAnimalsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShelterUseCase @Inject constructor(
    private val repo: RescuedAnimalsRepository
) {

    suspend operator fun invoke(upr_cd: String, org_cd: String): Flow<Result<List<Shelter>>> =
        repo.getShelter(upr_cd = upr_cd, org_cd = org_cd)

}