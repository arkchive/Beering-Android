package com.example.beering.api

import com.google.gson.annotations.SerializedName

// 리뷰 등록
data class AddReview(
    @SerializedName("category_id") val category_id: Int,
    @SerializedName("content") val content: String,
    @SerializedName("total_rating") val total_rating: Float,
    @SerializedName("selected_option") val selected_option: List<SelectedOption>
    )

data class SelectedOption(
    @SerializedName("review_option_id") val review_option_id: Int,
    @SerializedName("rating") val rating: Float
    )

// 리뷰 작성 폼
data class AddReviewOption(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ReviewResult
    )

data class ReviewResult(
    @SerializedName("review_options") val review_options: List<ReviewOptions>
)

data class ReviewOptions(
    @SerializedName("review_option_id") val review_option_id: Int,
    @SerializedName("name") val name: String
    )