package com.example.domain.entity

data class Result<out T>(
    val status: Status,
    val data: @UnsafeVariance T?,
    val message: String?
) {

    companion object {

        fun <T> success(data: T? = null): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Result<T> {
            return Result(Status.ERROR, data, message)
        }

        fun <T> fail(message: String): Result<T> {
            return Result(Status.FAIL, null, message)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(Status.LOADING, data, null)
        }
    }
}