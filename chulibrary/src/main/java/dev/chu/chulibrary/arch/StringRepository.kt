package dev.chu.chulibrary.arch

import androidx.annotation.StringRes

interface StringRepository {
    fun getString(@StringRes resId: Int): String
}