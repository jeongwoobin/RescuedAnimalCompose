package com.example.data.service

import com.example.data.model.remote.AnimalEntity
import com.example.data.model.remote.BaseResponse
import com.example.data.model.remote.ListBody
import com.example.data.model.remote.ShelterEntity
import com.example.data.model.remote.SigunguEntity
import com.example.data.model.remote.SidoEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RescuedAnimalsApi {

    @GET("sido")
    suspend fun fetchSido(
        @Query("numOfRows") numOfRows: Int = 20
    ): Response<BaseResponse<ListBody<SidoEntity>>>

    @GET("sigungu")
    suspend fun fetchSigungu(
        @Query("upr_cd") upr_cd: String
    ): Response<BaseResponse<ListBody<SigunguEntity>>>


    @GET("shelter")
    suspend fun fetchShelter(
        @Query("upr_cd") upr_cd: String, @Query("org_cd") org_cd: String
    ): Response<BaseResponse<ListBody<ShelterEntity>>>


    /**
     * 구조동물 조회 API
     *
     * @param upr_cd 시도 코드
     * @param org_cd 시군구 코드
     * @param care_reg_no 보호소 번호
     * @param bgnde 유기날짜(검색 시작일 (YYYYMMDD))
     * @param endde 유기날짜(검색 종료일 (YYYYMMDD))
     * @param upkind 축종코드
     * @param pageNo 페이지 번호
     * @param numOfRows 페이지당 보여줄 개수
     * @return
     */
    @GET("abandonmentPublic")
    suspend fun fetchRescuedAnimal(
        @Query("upr_cd") upr_cd: String?,
        @Query("org_cd") org_cd: String?,
        @Query("care_reg_no") care_reg_no: String?,
        @Query("bgnde") bgnde: String?,
        @Query("endde") endde: String?,
        @Query("upkind") upkind: Int?,
        @Query("neuter") neuter: String?,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BaseResponse<ListBody<AnimalEntity>>>
}