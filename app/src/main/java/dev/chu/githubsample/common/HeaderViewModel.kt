package dev.chu.githubsample.common

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.chu.chulibrary.arch.event.SingleLiveEvent
import dev.chu.chulibrary.util.extensions.changeValue
import javax.inject.Inject

class HeaderViewModel @Inject constructor() : ViewModel() {

    private val _headerTitle = SingleLiveEvent<String>()
    val headerTitle: LiveData<String> get() = _headerTitle
    fun setTitle(title: String) {
        _headerTitle.changeValue(title)
    }

    private val _headerLeftImage = SingleLiveEvent<Drawable?>()
    val headerLeftImage: LiveData<Drawable?> get() = _headerLeftImage
    fun setLeftImage(res: Drawable?) {
        _headerLeftImage.changeValue(res)
    }

    private val _clickHeaderLeftImage = SingleLiveEvent<Unit>()
    val clickHeaderLeftImage: LiveData<Unit> get() = _clickHeaderLeftImage
    fun onClickHeaderLeftImage() {
        _clickHeaderLeftImage.changeValue(Unit)
    }
}