package dev.chu.chulibrary.api

import android.os.Handler
import android.os.Looper
import dev.chu.chulibrary.core.BaseApplication
import dev.chu.chulibrary.util.extensions.toast
import java.io.IOException

/**
 * API 응답을 받아와 처리하기 위한 sealed class
 * 사용 이유 :
 * Retrofit 에서 suspend modifier 를 제공하기 시작했고, 굳이 Coroutines 를 Retrofit 과 함께 사용하는데,
 * jakeWharton 이 만든 CoroutineAdapter 를 사용할 필요가 없다.
 * 따라서, Custom CoroutineAdapter 를 만들어 사용한다.
 *
 * @param T : 성공 Response
 * @param U : 실패 Response
 *
 * 참고 : https://proandroiddev.com/create-retrofit-calladapter-for-coroutines-to-handle-response-as-states-c102440de37a
 */
sealed class BaseApiResponse<out T : Any, out U : Any> {
    /**
     * request 의 성공 상태의 body 를 포함하고 있는 data class
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : BaseApiResponse<T, Nothing>()

    /**
     * 2xx 이 아닌 response 를 나타내며, error body 와 response 상태 코드를 포함한다.
     * Failure response with body
     */
    data class ApiError<U : Any>(val body: U, val code: Int) : BaseApiResponse<Nothing, U>()

    /**
     * 인터넷 연결이 안될 경우처럼 네트워크 실패에 대해 나타낸다.
     * Network error
     */
    data class NetworkError(val error: IOException) : BaseApiResponse<Nothing, Nothing>()

    /**
     * request 를 만들거나 또는 response 를 처리하는 과정에서 발생하는 unexpected exceptions 나타난다.
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable?) : BaseApiResponse<Nothing, Nothing>()
}

fun <T : Any, U : Any> BaseApiResponse<T, U>.handled(): T? {
    when(this) {
        is BaseApiResponse.Success -> return this.body
        is BaseApiResponse.ApiError -> {
            Handler(Looper.getMainLooper()).post {
                BaseApplication.getInstance().toast("ApiError $body")
            }
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