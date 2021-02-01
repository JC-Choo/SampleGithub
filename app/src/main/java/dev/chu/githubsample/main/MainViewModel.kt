package dev.chu.githubsample.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.chu.chulibrary.arch.event.Event
import dev.chu.chulibrary.util.extensions.changeValue
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {

    private val _navigateToPureFragment = MutableLiveData<Event<Unit>>()
    val navigateToPureFragment: LiveData<Event<Unit>> = _navigateToPureFragment

    private val _navigateToGitHubPractice = MutableLiveData<Event<Unit>>()
    val navigateToGithubPractice: LiveData<Event<Unit>> = _navigateToGitHubPractice

    private val _navigateToLargeImage = MutableLiveData<Event<Unit>>()
    val navigateToLargeImage: LiveData<Event<Unit>> = _navigateToLargeImage

    private val _navigateToAuthFragment = MutableLiveData<Event<Unit>>()
    val navigateToAuthFragment: LiveData<Event<Unit>> = _navigateToAuthFragment

    private val _navigateToLogin = MutableLiveData<Event<Unit>>()
    val navigateToLoginFragment: LiveData<Event<Unit>> = _navigateToLogin

    fun onAuthFragmentClicked() = _navigateToAuthFragment.changeValue(Event(Unit))

    fun onPureFragmentClicked() = _navigateToPureFragment.changeValue(Event(Unit))

    fun onGithubPracticeClicked() = _navigateToGitHubPractice.changeValue(Event(Unit))

    fun onLargeImageFragmentClicked() = _navigateToLargeImage.changeValue(Event(Unit))

    fun onLoginFragmentClicked() = _navigateToLogin.changeValue(Event(Unit))
}