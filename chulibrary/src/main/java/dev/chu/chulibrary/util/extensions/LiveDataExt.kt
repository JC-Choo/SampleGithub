package dev.chu.chulibrary.util.extensions

import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import dev.chu.chulibrary.arch.event.EventObserver

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

/**
 * View 에서 liveData 관련 작업을 observe 처리를 쉽게 하기 위한 fun
 * ex)
 * viewModel.image.observe(this, Observer { str -> // Do Something }
 * -> observe(viewModel.image, ::showImage)
 */
fun <T> LifecycleOwner.observe(liveData: MutableLiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, { it?.let { t -> action(t) } })
}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, { it?.let { t -> action(t) } })
}