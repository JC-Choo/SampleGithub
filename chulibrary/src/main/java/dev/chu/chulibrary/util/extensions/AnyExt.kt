package dev.chu.chulibrary.util.extensions

import android.content.res.Resources
import dev.chu.chulibrary.arch.IdTextResource
import dev.chu.chulibrary.arch.SimpleTextResource
import dev.chu.chulibrary.arch.TextResource

val Any.TAG: String
    get() = this::class.java.simpleName ?: this.toString()

fun TextResource.asString(resources: Resources): String = when(this) {
    is SimpleTextResource -> this.text
    is IdTextResource -> resources.getString(this.resId)
}