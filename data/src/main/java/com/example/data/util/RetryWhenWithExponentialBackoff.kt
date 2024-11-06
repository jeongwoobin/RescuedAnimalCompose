package com.example.data.util

import androidx.annotation.FloatRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.retryWhen
import kotlin.math.pow


// initialDelay를 retryFactor배 만큼 증가시켜서 retry
fun <T> Flow<T>.retryWhen(
    @FloatRange(from = 0.0) initialDelay: Float = 100f,
    @FloatRange(from = 1.0) retryFactor: Float = 2.0f,
    predicate: suspend FlowCollector<T>.(cause: Throwable, retryCount: Long, delayTime: Long) -> Boolean
): Flow<T> = this.retryWhen { cause, retryCount ->
    val retryDelay = initialDelay * retryFactor.pow(retryCount.toFloat())
    predicate(cause, retryCount, retryDelay.toLong())
}