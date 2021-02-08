package dev.chu.chulibrary.api

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

/**
 * Custom CallAdapter 를 만들기 위한 첫번째 단계
 * [retrofit2.Call] interface 를 구현
 * [enqueue] : 비동기적으로 request 를 보내고 response 의 callback 또는 서버와 통신하거나, request 를 생성하거나, response 을 처리하는 중에 오류가 발생하면 알린다.
 *
 */
class BaseApiResponseCall<S : Any, E : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<BaseApiResponse<S, E>> {

    override fun enqueue(callback: Callback<BaseApiResponse<S, E>>) {
        return delegate.enqueue(object : Callback<S> {
            /**
             * HTTP 응답을 받는 것에 대한 호출
             * 응답은 성공 또는 실패로 나뉜다.
             */
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@BaseApiResponseCall,
                            Response.success(BaseApiResponse.Success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@BaseApiResponseCall,
                            Response.success(BaseApiResponse.UnknownError(body))
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(error)
                        } catch (e: Exception) {
                            null
                        }
                    }

                    if (errorBody != null) {
                        callback.onResponse(
                            this@BaseApiResponseCall,
                            Response.success(BaseApiResponse.ApiError(errorBody, code))
                        )
                    } else {
                        callback.onResponse(
                            this@BaseApiResponseCall,
                            Response.success(BaseApiResponse.UnknownError(null))
                        )
                    }
                }
            }

            /**
             * 서버와 통신할 때 발생하는 NetworkException 또는 요청 생성을 만들거나 응답을 처리하는 과정에서 발생한 UnexpectedException 를 호출
             *
             */
            override fun onFailure(call: Call<S>, t: Throwable) {
                val networkRes = when(t) {
                    is IOException -> BaseApiResponse.NetworkError(t)
                    else -> BaseApiResponse.UnknownError(t)
                }

                callback.onResponse(this@BaseApiResponseCall, Response.success(networkRes))
            }
        })
    }

    /**
     * 남은 메소드들은 original call 에 위임하면 된다.
     */
    override fun clone(): Call<BaseApiResponse<S, E>> = BaseApiResponseCall(delegate.clone(), errorConverter)
    override fun isExecuted(): Boolean = delegate.isExecuted
    override fun cancel() = delegate.cancel()
    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()
    override fun execute(): Response<BaseApiResponse<S, E>> {
        throw UnsupportedOperationException("BaseApiResponseCall doesn't support execute")
    }
}