package com.example.domain.usecase

import com.example.domain.entity.Animal
import com.example.domain.entity.Result
import com.example.domain.repository.local.FavoriteAnimalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFavoriteAnimalUseCase @Inject constructor(
    private val repo: FavoriteAnimalRepository
) {

    suspend operator fun invoke(favoriteAnimal: Animal): Flow<Result<Boolean>> =
        repo.deleteFavoriteAnimal(
            desertionNo = favoriteAnimal.desertionNo
        )

    suspend fun deleteAll(): Flow<Result<Boolean>> = repo.deleteAll()
}