package com.example.domain.entity

import kotlinx.serialization.Serializable

/**
 * api 호출 시 적용할 필터
 *
 * @property bgnde 유기날짜(검색 시작일 (YYYYMMDD))
 * @property endde 유기날짜(검색 종료일 (YYYYMMDD))
 * @property upkind 축종코드
 * @property neuter 중성화 여부
 * @property pageNo 페이지 번호
 * @property numOfRows 페이지당 보여줄 개수
 */
@Serializable
data class AnimalSearchFilter(
    var bgnde: String? = null,
    var endde: String? = null,
    var upkind: Upkind? = null ?: Upkind.ALL,
    var neuter: Neuter? = null ?: Neuter.ALL,
    var pageNo: Int = 1,
) {
    val numOfRows: Int = if (pageNo != 1) 20 else 40
}

enum class Upkind(val id: Int?) {
    ALL(id = null), DOG(id = 417000), CAT(id = 422400), ETC(id = 429900)
}

enum class Neuter(val neuter: String?) {
    ALL(neuter = null), YES(neuter = "Y"), NO(neuter = "N"), UNKNOWN(neuter = "U")
}