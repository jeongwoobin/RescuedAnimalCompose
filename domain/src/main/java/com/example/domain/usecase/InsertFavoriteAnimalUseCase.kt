package com.example.domain.usecase

import com.example.domain.entity.Animal
import com.example.domain.entity.Result
import com.example.domain.repository.local.FavoriteAnimalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertFavoriteAnimalUseCase@Inject constructor(
    private val repo: FavoriteAnimalRepository
) {

    suspend operator fun invoke(favoriteAnimal: Animal): Flow<Result<Boolean>> {
        return repo.insertFavoriteAnimal(favoriteAnimal = favoriteAnimal)
    }
}