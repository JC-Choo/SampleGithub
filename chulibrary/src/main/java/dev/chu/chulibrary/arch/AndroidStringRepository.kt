package dev.chu.chulibrary.arch

import android.content.res.Resources
import androidx.annotation.StringRes

class AndroidStringRepository(
    private val resources: Resources
) : StringRepository {
    override fun getString(@StringRes resId: Int): String = resources.getString(resId)
}