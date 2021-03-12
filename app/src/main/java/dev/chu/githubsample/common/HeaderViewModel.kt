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

    private val _isShowImageRight = MutableLiveData(false)
    val isShowImageRight: LiveData<Boolean> get() = _isShowImageRight
    fun showImageRight(isShow: Boolean) {
        _isShowImageRight.changeValue(isShow)
    }

    private val _headerImageLeft = SingleLiveEvent<Drawable?>()
    val headerImageLeft: LiveData<Drawable?> get() = _headerImageLeft
    fun setImageLeft(res: Drawable?) {
        _headerImageLeft.changeValue(res)
    }

    private val _clickHeaderImageLeft = SingleLiveEvent<Unit>()
    val clickHeaderImageLeft: LiveData<Unit> get() = _clickHeaderImageLeft
    fun onClickHeaderImageLeft() {
        _clickHeaderImageLeft.changeValue(Unit)
    }

    private val _headerImageRight = SingleLiveEvent<Drawable?>()
    val headerImageRight: LiveData<Drawable?> get() = _headerImageRight
    fun setImageRight(res: Drawable?) {
        _headerImageRight.changeValue(res)
    }

    private val _clickHeaderImageRight = SingleLiveEvent<Unit>()
    val clickHeaderImageRight: LiveData<Unit> get() = _clickHeaderImageRight
    fun onClickHeaderImageRight() {
        _clickHeaderImageRight.changeValue(Unit)
    }
}