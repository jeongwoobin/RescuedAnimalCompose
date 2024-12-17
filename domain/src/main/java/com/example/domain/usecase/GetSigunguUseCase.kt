package com.example.domain.usecase

import com.example.domain.entity.Result
import com.example.domain.entity.Sido
import com.example.domain.entity.Sigungu
import com.example.domain.repository.remote.RescuedAnimalsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSigunguUseCase @Inject constructor(
    private val repo: RescuedAnimalsRepository
) {

    suspend operator fun invoke(upr_cd: String): Flow<Result<List<Sigungu>>> = repo.getSigungu(upr_cd = upr_cd)

}