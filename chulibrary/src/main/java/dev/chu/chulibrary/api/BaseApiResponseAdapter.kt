package dev.chu.chulibrary.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class BaseApiResponseAdapter<S: Any, E: Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, E>
): CallAdapter<S, Call<BaseApiResponse<S, E>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<BaseApiResponse<S, E>> {
        return BaseApiResponseCall(call, errorBodyConverter)
    }
}