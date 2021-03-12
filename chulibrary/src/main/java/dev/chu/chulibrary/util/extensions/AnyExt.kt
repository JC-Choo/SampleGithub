package dev.chu.chulibrary.util.extensions

import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import dev.chu.chulibrary.api.BaseApiResponse
import dev.chu.chulibrary.arch.IdTextResource
import dev.chu.chulibrary.arch.SimpleTextResource
import dev.chu.chulibrary.arch.TextResource
import dev.chu.chulibrary.core.BaseApplication

val Any.TAG: String
    get() = this::class.java.simpleName ?: this.toString()

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