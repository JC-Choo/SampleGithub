package dev.chu.chulibrary.api

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class BaseApiResponseCall<S : Any, E : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<BaseApiResponse<S, E>> {

    override fun enqueue(callback: Callback<BaseApiResponse<S, E>>) {
        return delegate.enqueue(object : Callback<S> {
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

            override fun onFailure(call: Call<S>, t: Throwable) {
                val networkRes = when(t) {
                    is IOException -> BaseApiResponse.NetworkError(t)
                    else -> BaseApiResponse.UnknownError(t)
                }

                callback.onResponse(this@BaseApiResponseCall, Response.success(networkRes))
            }
        })
    }

    override fun clone(): Call<BaseApiResponse<S, E>> = BaseApiResponseCall(delegate, errorConverter)

    override fun execute(): Response<BaseApiResponse<S, E>> {
        throw UnsupportedOperationException("BaseApiResponseCall doesn't support execute")
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}