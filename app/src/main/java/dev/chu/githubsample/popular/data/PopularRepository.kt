package dev.chu.githubsample.popular.data

import dev.chu.chulibrary.arch.data.BaseRepository
import dev.chu.chulibrary.util.time.minutes
import dev.chu.githubsample.data.entity.GithubResult
import dev.chu.githubsample.data.entity.Repository
import javax.inject.Inject

class PopularRepository @Inject constructor(
    dataSource: PopularDataSource
) : BaseRepository<Unit, GithubResult<Repository>>(
    remoteDataSource = dataSource,
    cachingDuration = 10.minutes
)