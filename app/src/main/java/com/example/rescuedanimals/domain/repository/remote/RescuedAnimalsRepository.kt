package com.example.rescuedanimals.domain.repository.remote

import com.example.rescuedanimals.domain.entity.Result
import com.example.rescuedanimals.domain.entity.Sido

interface RescuedAnimalsRepository {
    suspend fun getSido(): Result<List<Sido>>
}