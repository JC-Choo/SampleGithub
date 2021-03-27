package dev.chu.chulibrary.util.extensions

import android.os.Looper
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.changeValue(newValue: T) {
    if (Thread.currentThread() == Looper.getMainLooper().thread) {
        value = newValue
    } else {
        postValue(newValue)
    }
}