package dev.chu.githubsample.main

import androidx.lifecycle.LiveData
import dev.chu.chulibrary.arch.event.SingleLiveEvent
import androidx.lifecycle.ViewModel
import dev.chu.chulibrary.util.extensions.changeValue
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    private val _navigateToPopular = SingleLiveEvent<Unit>()
    val navigateToPopular: LiveData<Unit>
        get() = _navigateToPopular

    fun onClickToPopular() {
        _navigateToPopular.changeValue(Unit)
    }
}