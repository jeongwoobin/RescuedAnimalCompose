package com.example.domain.usecase

import com.example.rescuedanimals.domain.entity.Animal
import com.example.rescuedanimals.domain.entity.Result
import com.example.rescuedanimals.domain.repository.local.FavoriteAnimalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertFavoriteAnimalUseCase@Inject constructor(
    private val repo: FavoriteAnimalRepository
) {

    suspend operator fun invoke(favoriteAnimal: Animal): Flow<Result<Boolean>> {
        return repo.insertFavoriteAnimal(favoriteAnimal = favoriteAnimal)
    }
}