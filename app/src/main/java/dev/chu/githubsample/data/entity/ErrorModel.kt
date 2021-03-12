package dev.chu.githubsample.data.entity

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("documentation_url")
    val documentationUrl: String?,
    @SerializedName("errors")
    val errors: List<Error>?,
    @SerializedName("message")
    val message: String?
)

data class Error(
    @SerializedName("code")
    val code: String,
    @SerializedName("field")
    val `field`: String,
    @SerializedName("resource")
    val resource: String
)