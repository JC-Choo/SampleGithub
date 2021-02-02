package dev.chu.chulibrary.util.extensions

import android.content.res.Resources
import dev.chu.chulibrary.arch.IdTextResource
import dev.chu.chulibrary.arch.SimpleTextResource
import dev.chu.chulibrary.arch.TextResource

/**
 * 해당 파일의 class name
 */
val Any.TAG: String
    get() = this::class.java.simpleName ?: this.toString()

/**
 * repository or viewModel 에서 resource id 를 가져와 getString 으로 보여주기 위해 사용
 */
fun TextResource.asString(resources: Resources): String = when(this) {
    is SimpleTextResource -> this.text
    is IdTextResource -> resources.getString(this.resId)
}

/**
 * 공통으로 사용할 Extension fun
 */
fun Any?.isNull() = this == null
fun Int?.orZero(): Int = this ?: 0
fun Long?.orZero(): Long = this ?: 0L
fun <T> T?.orDefault(default: T): T = this ?: default