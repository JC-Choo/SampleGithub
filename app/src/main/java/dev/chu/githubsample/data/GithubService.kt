package dev.chu.githubsample.data

import dev.chu.chulibrary.api.BaseApiResponse
import dev.chu.githubsample.data.entity.ErrorModel
import dev.chu.githubsample.data.entity.GithubResult
import dev.chu.githubsample.data.entity.Repository
import dev.chu.githubsample.data.entity.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories?q=stars:>1&sort=stars&per_page=100")
    suspend fun getPopularRepos(): BaseApiResponse<GithubResult<Repository>, ErrorModel>

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") user: String,
        @Query("page") page: Int = 1
    ): BaseApiResponse<GithubResult<User>, ErrorModel>

    @GET("users/{user}/repos")
    suspend fun repos(
        @Path("user") user: String
    ): BaseApiResponse<List<Repository>, ErrorModel>

    @GET("search/repositories")
    suspend fun searchRepos(
        @Query("q") query: String
    ): BaseApiResponse<GithubResult<Repository>, ErrorModel>
}