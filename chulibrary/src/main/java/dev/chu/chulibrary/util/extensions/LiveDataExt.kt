package dev.chu.chulibrary.util.extensions

import android.os.Looper
import androidx.lifecycle.MutableLiveData

/**
 * [Thread]에 따라 [MutableLiveData.setValue] or [MutableLiveData.postValue]로 처리한다.
 */
fun <T> MutableLiveData<T>.changeValue(newValue: T?) {
    if (Thread.currentThread() == Looper.getMainLooper().thread) {
        value = newValue
    } else {
        postValue(newValue)
    }
}