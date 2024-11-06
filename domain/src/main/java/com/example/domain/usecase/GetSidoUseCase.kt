package com.example.domain.usecase

import com.example.domain.entity.Result
import com.example.domain.entity.Sido
import com.example.domain.repository.remote.RescuedAnimalsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSidoUseCase @Inject constructor(
    private val repo: RescuedAnimalsRepository
) {

    suspend operator fun invoke(): Flow<Result<List<Sido>>> = repo.getSido()

}