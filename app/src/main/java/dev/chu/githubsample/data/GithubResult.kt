package dev.chu.githubsample.data

import com.google.gson.annotations.SerializedName

/**
 * Github Api 검색결과 모델.
 */
data class GithubResult<T>(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<T>
)