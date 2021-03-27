package dev.chu.chulibrary.util.extensions

import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import dev.chu.chulibrary.api.BaseApiResponse
import dev.chu.chulibrary.arch.IdTextResource
import dev.chu.chulibrary.arch.SimpleTextResource
import dev.chu.chulibrary.arch.TextResource
import dev.chu.chulibrary.core.BaseApplication

fun <S : Any, E : Any> BaseApiResponse<S, E>.handled(): S? {
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

fun TextResource.asString(resources: Resources): String = when(this) {
    is SimpleTextResource -> this.text
    is IdTextResource -> resources.getString(this.resId)
}

/**
 * 해당 파일의 class name
 */
val Any.TAG: String
    get() = this::class.java.simpleName ?: this.toString()

/**
 * 공통으로 사용할 Extension fun
 */
fun Any?.isNull() = this == null
fun Int?.orZero(): Int = this ?: 0
fun Long?.orZero(): Long = this ?: 0L
fun <T> T?.orDefault(default: T): T = this ?: default