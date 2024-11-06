package com.example.domain.usecase

import com.example.domain.entity.Animal
import com.example.domain.entity.ListBodyEntity
import com.example.domain.entity.Result
import com.example.domain.repository.remote.RescuedAnimalsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRescuedAnimalUseCase @Inject constructor(
    private val repo: RescuedAnimalsRepository
) {

    suspend operator fun invoke(
        pageNo: Int,
        numOfRows: Int
    ): Flow<Result<ListBodyEntity<Animal>>> = repo.getRescuedAnimal(
        pageNo = pageNo,
        numOfRows = numOfRows
    )
}