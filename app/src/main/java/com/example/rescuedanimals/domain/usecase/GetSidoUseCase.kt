package com.example.rescuedanimals.domain.usecase

import com.example.rescuedanimals.domain.entity.Result
import com.example.rescuedanimals.domain.entity.Sido
import com.example.rescuedanimals.domain.repository.remote.RescuedAnimalsRepository
import javax.inject.Inject

class GetSidoUseCase @Inject constructor(
    private val repo: RescuedAnimalsRepository
) {

    suspend operator fun invoke(): Result<List<Sido>> {
        return repo.getSido()
    }
}