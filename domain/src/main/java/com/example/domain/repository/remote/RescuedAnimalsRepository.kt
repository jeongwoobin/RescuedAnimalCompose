package com.example.domain.repository.remote

import com.example.domain.entity.Result
import com.example.domain.entity.Sido

interface RescuedAnimalsRepository {
    suspend fun getSido(): Result<List<Sido>>
}