package com.example.domain.entity

import kotlinx.serialization.Serializable

/**
 * api 호출 시 적용할 필터
 *
 * @property upr_cd 시도 코드
 * @property bgnde 유기날짜(검색 시작일 (YYYYMMDD))
 * @property endde 유기날짜(검색 종료일 (YYYYMMDD))
 * @property upkind 축종코드
 * @property neuter 중성화 여부
 * @property state 상태
 * @property pageNo 페이지 번호
 * @property numOfRows 페이지당 보여줄 개수
 */
@Serializable
data class AnimalSearchFilter(
    var upr_cd: Sido? = null,
    var bgnde: String? = null,
    var endde: String? = null,
    var upkind: Upkind? = null ?: Upkind.ALL,
    var neuter: Neuter? = null ?: Neuter.ALL,
    var state: State? = null ?: State.ALL,
    var pageNo: Int = 1,
) {
    val numOfRows: Int = if (pageNo != 1) 20 else 40
}

enum class Upkind(val id: Int?, val kor: String) {
    ALL(id = null, kor = "전체"), DOG(id = 417000, kor = "개"), CAT(id = 422400, kor = "고양이"), ETC(id = 429900, kor = "기타")
}

enum class Neuter(val neuter: String?, val kor: String) {
    ALL(neuter = null, kor = "전체"), YES(neuter = "Y", kor = "예"), NO(neuter = "N", kor = "아니오"), UNKNOWN(neuter = "U", kor = "미상")
}

enum class State(val state: String?, val kor: String) {
    ALL(state = null, kor = "전체"), NOTICE(state = "notice", kor = "공고중"), PROTECT(state = "protect", kor = "보호중")
}