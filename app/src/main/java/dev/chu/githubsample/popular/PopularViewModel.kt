package dev.chu.githubsample.popular

import androidx.lifecycle.ViewModel
import dev.chu.githubsample.popular.data.PopularRepository
import javax.inject.Inject

class PopularViewModel @Inject constructor(
    repository: PopularRepository
) : ViewModel() {
}