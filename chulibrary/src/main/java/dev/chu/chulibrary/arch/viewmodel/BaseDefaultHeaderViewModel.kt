package dev.chu.chulibrary.arch.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.chu.chulibrary.arch.event.Event
import dev.chu.chulibrary.util.extensions.changeValue
import javax.inject.Inject

open class BaseDefaultHeaderViewModel @Inject constructor() : ViewModel() {
    private val _title: MutableLiveData<String> = MutableLiveData()
    val title: LiveData<String>
        get() = _title

    fun setTitle(title: String) {
        _title.changeValue(title)
    }

    var image = MutableLiveData<Drawable>()
        set(value) {
            field.changeValue(value.value)
            field = value
        }

    private val _clickEvent = MutableLiveData<Event<String>>()
    val clickEvent: LiveData<Event<String>>
        get() = _clickEvent

    fun onClickTitle() {
        _clickEvent.changeValue(Event("title"))
    }

    fun onClickImage() {
        _clickEvent.changeValue(Event("image"))
    }
}