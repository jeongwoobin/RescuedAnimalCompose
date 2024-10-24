package com.example.rescuedanimals.domain.usecase

import com.example.rescuedanimals.domain.entity.Animal
import com.example.rescuedanimals.domain.entity.Result
import com.example.rescuedanimals.domain.repository.local.FavoriteAnimalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteFavoriteAnimalUseCase @Inject constructor(
    private val repo: FavoriteAnimalRepository
) {

    suspend operator fun invoke(favoriteAnimal: Animal): Flow<Result<Boolean>> {
        return try {
            repo.deleteFavoriteAnimal(
                desertionNo = favoriteAnimal.desertionNo ?: throw NullPointerException()
            )
        } catch (e: NullPointerException) {
            flow { emit(Result.error(message = e.message ?: "DesertionNo is Null", data = false)) }
        }
    }

    suspend fun deleteAll(): Flow<Result<Boolean>> {
        return repo.deleteAll()
    }
}