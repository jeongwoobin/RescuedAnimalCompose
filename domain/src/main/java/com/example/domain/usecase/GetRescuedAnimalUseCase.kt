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

    /**
     * GetRescuedAnimal
     *
     * @param bgnde 유기날짜(검색 시작일 (YYYYMMDD))
     * @param endde 유기날짜(검색 종료일 (YYYYMMDD))
     * @param upkind 축종코드
     * @param pageNo 페이지 번호
     * @param numOfRows 페이지당 보여줄 개수
     * @return
     */
    suspend operator fun invoke(
        bgnde: String? = null,
        endde: String? = null,
        upkind: Int? = null,
        pageNo: Int,
        numOfRows: Int
    ): Flow<Result<ListBodyEntity<Animal>>> = repo.getRescuedAnimal(
        bgnde = bgnde, endde = endde, upkind = upkind, pageNo = pageNo, numOfRows = numOfRows
    )
}