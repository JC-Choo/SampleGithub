package dev.chu.chulibrary.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.chu.chulibrary.R

class TestViewModel02(
    private val backend: TestBackend
): ViewModel() {
    val textToDisplay: MutableLiveData<TextResource> = MutableLiveData()

    fun loadText() {
        try {
            val text: String = backend.getText()
            textToDisplay.postValue(TextResource.fromText(text))
        } catch (e: Throwable) {
            textToDisplay.postValue(TextResource.fromStringResId(R.string.app_name))
        }
    }
}

class TestViewModel01(
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