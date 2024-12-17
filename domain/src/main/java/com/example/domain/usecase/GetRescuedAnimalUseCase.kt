package com.example.domain.usecase

import com.example.domain.entity.Animal
import com.example.domain.entity.AnimalSearchFilter
import com.example.domain.entity.ListBody
import com.example.domain.entity.Result
import com.example.domain.repository.remote.RescuedAnimalsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRescuedAnimalUseCase @Inject constructor(
    private val repo: RescuedAnimalsRepository
) {

    /**
     * GetRescuedAnimal
     *
     * @param animalSearchFilter 검색 파라미터
     * @return
     */
    suspend operator fun invoke(
        animalSearchFilter: AnimalSearchFilter
    ): Flow<Result<ListBody<Animal>>> = repo.getRescuedAnimal(
        animalSearchFilter = animalSearchFilter
    )
}