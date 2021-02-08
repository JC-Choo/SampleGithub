package dev.chu.chulibrary.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

/**
 * Custom CallAdapter 를 만들기 위한 두번째 단계
 * [responseType] : HTTP 응답 바디를 Java Object 로 변환할 때 이 어댑터가 사용하는 value type 을 반환
 * [adapt] : Call 을 위임하는 T 인스턴스를 반환한다. 우리가 막 만든 [BaseApiResponseCall] 을 여기서 사용한다.
 */
class BaseApiResponseAdapter<S: Any, E: Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, E>
): CallAdapter<S, Call<BaseApiResponse<S, E>>> {

    /**
     * adapter 가 HtTTP 응답 바디를 자바 객체로 변환할 때 사용하는 value type 을 반환한다.
     */
    override fun responseType(): Type = successType

    /**
     * 호출을 위임하는 [S] 의 인스턴스를 반환하고, 여기서 우리가 막 만든 [BaseApiResponseCall]를 사용할 것
     */
    override fun adapt(call: Call<S>): Call<BaseApiResponse<S, E>> {
        return BaseApiResponseCall(call, errorBodyConverter)
    }
}