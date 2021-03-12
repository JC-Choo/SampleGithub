package dev.chu.githubsample.popular.data

import dev.chu.chulibrary.arch.data.RemoteDataSource
import dev.chu.chulibrary.concurrent.AppDispatchers
import dev.chu.chulibrary.util.extensions.handled
import dev.chu.githubsample.data.GithubService
import dev.chu.githubsample.data.entity.GithubResult
import dev.chu.githubsample.data.entity.Repository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PopularDataSource @Inject constructor(
    private val service: GithubService
) : RemoteDataSource<Unit, GithubResult<Repository>> {

    override suspend fun fetchData(
        request: Unit,
        isForceRefresh: Boolean
    ): GithubResult<Repository>? {
        return withContext(AppDispatchers.IO) {
            val handled = service.getPopularRepos().handled()
            handled
        }
    }
}