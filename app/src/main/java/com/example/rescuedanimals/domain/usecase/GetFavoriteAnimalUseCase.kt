package com.example.rescuedanimals.domain.usecase

import com.example.rescuedanimals.domain.entity.Animal
import com.example.rescuedanimals.domain.entity.Result
import com.example.rescuedanimals.domain.repository.local.FavoriteAnimalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteAnimalUseCase@Inject constructor(
    private val repo: FavoriteAnimalRepository
) {

    suspend operator fun invoke(): Flow<Result<List<Animal>>> {
        return repo.getAll()
    }
}