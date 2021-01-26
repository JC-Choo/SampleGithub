package dev.chu.chulibrary.arch

import android.content.res.Resources
import androidx.annotation.StringRes

interface StringRepository {
    fun getString(@StringRes resId: Int): String
}

class AndroidStringRepository(
    private val resources: Resources
) : StringRepository {
    override fun getString(@StringRes resId: Int): String = resources.getString(resId)
}

class TestDoubleStringRepository: StringRepository {
    override fun getString(@StringRes resId: Int): String = "some string!"
}

class TestDoubleBackend: TestBackend()

open class TestBackend {

    var failWhenLoadingText: Boolean = false

    fun getText(): String {
        return if (failWhenLoadingText) "some string?" else throw Throwable("failWhenLoadingText is false!")
    }
}