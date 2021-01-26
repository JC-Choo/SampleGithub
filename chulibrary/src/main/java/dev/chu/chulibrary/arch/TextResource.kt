package dev.chu.chulibrary.arch

import androidx.annotation.StringRes

/**
 * 참고 : https://hannesdorfmann.com/abstraction-text-resource/
 */
sealed class TextResource {
    companion object {
        fun fromText(text: String): TextResource = SimpleTextResource(text)
        fun fromStringResId(@StringRes resId: Int): TextResource = IdTextResource(resId)
    }
}

data class SimpleTextResource(
    val text: String
) : TextResource()

data class IdTextResource(
    @StringRes val resId: Int
) : TextResource()