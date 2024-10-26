package com.example.domain.usecase

import com.example.domain.entity.Result
import com.example.domain.entity.Sido
import com.example.domain.repository.remote.RescuedAnimalsRepository
import javax.inject.Inject

class GetSidoUseCase @Inject constructor(
    private val repo: RescuedAnimalsRepository
) {

    suspend operator fun invoke(): Result<List<Sido>> {
        return repo.getSido()
    }
}