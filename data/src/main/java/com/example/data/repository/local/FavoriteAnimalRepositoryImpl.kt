package com.example.data.repository.local

import com.example.data.datasource.local.FavoriteAnimalDataSource
import com.example.data.mapper.DBAnimalMapper
import com.example.domain.entity.Animal
import com.example.domain.repository.local.FavoriteAnimalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.domain.entity.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class FavoriteAnimalRepositoryImpl @Inject constructor(
    private val favoriteAnimalDataSource: FavoriteAnimalDataSource
) : FavoriteAnimalRepository {
    override suspend fun selectAll(): Result<List<Animal>> = try {
        Result.success(DBAnimalMapper.mapperToAnimalList(favoriteAnimalDataSource.selectAll()))
    } catch (e: Exception) {
        Result.fail(message = "저장된 데이터를 가져오는데 실패했습니다.")
    }
//            .map { value ->
//                Result.success(DBAnimalMapper.mapperToAnimalList(value))
//            }.catch {
//                emit(Result.fail(message = "저장된 데이터를 가져오는데 실패했습니다."))
//            }

    override suspend fun selectFavoriteAnimal(desertionNo: Long): Flow<Result<Animal>> =
        favoriteAnimalDataSource.selectFavoriteAnimal(desertionNo = desertionNo)
            .map { value -> Result.success(DBAnimalMapper.mapperToAnimal(value)) }.catch {
                emit(Result.fail(message = "저장된 데이터를 가져오는데 실패했습니다."))
            }

    override suspend fun insertFavoriteAnimal(favoriteAnimal: Animal): Flow<Result<Boolean>> =
        favoriteAnimalDataSource.insertFavoriteAnimal(
            favoriteAnimal = DBAnimalMapper.mapperToAnimalEntity(
                favoriteAnimal
            )
        ).map { value ->
            if (value != 0L) Result.success(true)
            else Result.error(message = "데이터 저장 오류")
        }.catch {
            emit(Result.fail(message = "데이터를 저장하는데 실패했습니다."))
        }

    override suspend fun deleteFavoriteAnimal(desertionNo: Long): Flow<Result<Boolean>> =
        favoriteAnimalDataSource.deleteFavoriteAnimal(desertionNo = desertionNo).map { value ->
                if (value != 0) (Result.success(true))
                else Result.error(message = "데이터 삭제 오류")
            }.catch {
                emit(Result.fail(message = "데이터를 삭제하는데 실패했습니다."))
            }

    override suspend fun deleteAll(): Flow<Result<Boolean>> =
        favoriteAnimalDataSource.deleteAll().map { value ->
                if (value != 0) Result.success(true)
                else Result.fail(message = "데이터 삭제 오류패")
            }.catch {
                emit(Result.fail(message = "데이터를 삭제하는데 실패했습니다."))
            }
}