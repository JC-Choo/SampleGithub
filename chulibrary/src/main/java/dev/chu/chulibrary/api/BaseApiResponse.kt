package dev.chu.chulibrary.api

import java.io.IOException

/**
 * API 응답을 받아와 처리하기 위한 sealed class
 *
 * @param T : 성공 Response
 * @param U : 실패 Response
 *
 * 참고 : https://proandroiddev.com/create-retrofit-calladapter-for-coroutines-to-handle-response-as-states-c102440de37a
 */
sealed class BaseApiResponse<out T : Any, out U : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : BaseApiResponse<T, Nothing>()

    /**
     * Failure response with body
     */
    data class ApiError<U : Any>(val body: U, val code: Int) : BaseApiResponse<Nothing, U>()

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : BaseApiResponse<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable?) : BaseApiResponse<Nothing, Nothing>()
}

fun <T : Any, U : Any> BaseApiResponse<T, U>.handled(): T? {
    when(this) {
        is BaseApiResponse.Success -> return this.body
        is BaseApiResponse.ApiError -> {

        }
        is BaseApiResponse.NetworkError -> {
            error.message
        }
        is BaseApiResponse.UnknownError -> {
            error?.message
        }
    }
    return null
}