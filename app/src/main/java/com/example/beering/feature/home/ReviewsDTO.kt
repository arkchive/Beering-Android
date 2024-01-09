package com.example.beering.feature.home

import com.google.gson.annotations.SerializedName

data class ReviewsResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("responseCode") val responseCode: Int,
    @SerializedName("responseMessage") val responseMessage: String,
    @SerializedName("result") val result: ReviewsResult
)

data class ReviewsResult(
    @SerializedName("content") val content: List<ReviewsContent>,
    @SerializedName("page") val page: Int,
    @SerializedName("last") val last: Boolean,
)

data class ReviewsContent(
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("profileImage") val profileImage: String,
    @SerializedName("reviewId") val reviewId: String,
    @SerializedName("reviewImageUrls") val reviewImageUrls: List<String>,
    @SerializedName("content") val content: String,
    @SerializedName("diffFromCurrentTime") val diffFromCurrentTime: String,
    @SerializedName("like") val like: Int,
    @SerializedName("dislike") val dislike: Int,
    @SerializedName("isTabomed") val isTabomed: String
    )