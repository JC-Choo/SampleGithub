package dev.chu.githubsample.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.chu.chulibrary.arch.event.Event
import dev.chu.chulibrary.util.extensions.changeValue
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {

    private val _navigateToGitHubPractice = MutableLiveData<Event<Unit>>()
    val navigateToGithubPractice: LiveData<Event<Unit>> = _navigateToGitHubPractice

    private val _navigateToLargeImage = MutableLiveData<Event<Unit>>()
    val navigateToLargeImage: LiveData<Event<Unit>> = _navigateToLargeImage

    fun onGithubPracticeClicked() = _navigateToGitHubPractice.changeValue(Event(Unit))

    fun onLargeImageFragmentClicked() = _navigateToLargeImage.changeValue(Event(Unit))
}