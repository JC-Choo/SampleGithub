package dev.chu.chulibrary.arch

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.chu.chulibrary.R

class TestDoubleStringRepository: StringRepository {
    override fun getString(@StringRes resId: Int): String = "some string!"
}

class TestDoubleBackend: TestBackend()

class TestViewModel(
    private val backend: TestBackend,
    private val stringRepo: StringRepository
): ViewModel() {
    val textToDisplay: MutableLiveData<String> = MutableLiveData()

    fun loadText() {
        try {
            val text: String = backend.getText()
            textToDisplay.value = text
        } catch (e: Throwable) {
            textToDisplay.value = stringRepo.getString(R.string.app_name)
        }
    }
}

open class TestBackend {

    var failWhenLoadingText: Boolean = false

    fun getText(): String {
        return if (failWhenLoadingText) "some string?" else throw Throwable("failWhenLoadingText is false!")
    }
}