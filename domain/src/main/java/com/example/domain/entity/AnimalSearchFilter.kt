package com.example.domain.entity

/**
 * api 호출 시 적용할 필터
 *
 * @property bgnde 유기날짜(검색 시작일 (YYYYMMDD))
 * @property endde 유기날짜(검색 종료일 (YYYYMMDD))
 * @property upkind 축종코드
 * @property pageNo 페이지 번호
 * @property numOfRows 페이지당 보여줄 개수
 */
data class AnimalSearchFilter(
    var bgnde: String? = null,
    var endde: String? = null,
    var upkind: Int? = null,
    var pageNo: Int = 1,
) {
    val numOfRows: Int = if (pageNo != 1) 20 else 40
}

sealed class Upkind(val id: Int) {
    data object Dog: Upkind(id = 417000)
    data object Cat: Upkind(id = 422400)
    data object Etc: Upkind(id = 429900)
}