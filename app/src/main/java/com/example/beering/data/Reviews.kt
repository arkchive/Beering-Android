package com.example.beering.data

import com.google.gson.annotations.SerializedName

data class Home(
    @SerializedName("reviews") val reviews: HomeItem
)

data class HomeItem(
    @SerializedName("memberId") val memberId:String,
    @SerializedName("profileImages") val profileImages: String,
    @SerializedName("reviewId") val reviewId: String,
    @SerializedName("ReviewImages") val ReviewImages: List<Images>,
    @SerializedName("content") val content: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("like") val like: Int,
    @SerializedName("dislike") val dislike: Int
)

data class MyReviews(
    @SerializedName("reviews") val reviews: MyReviewsItem
    )

data class MyReviewsItem(
    @SerializedName("reviewId") val reviewId: String,
    @SerializedName("images") val images: List<Images>,
    @SerializedName("content") val content: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("like") val like: Int,
    @SerializedName("dislike") val dislike: Int
)

data class Images(
    @SerializedName("images") val images:String
)

data class DrinkReviews(
    @SerializedName("reviews") val reviews: List<DrinkReviewsItem>
    )

data class DrinkReviewsItem(
    @SerializedName("reviewId") val reviewId: String,
    @SerializedName("images") val images: List<Images>,
    @SerializedName("memberInfo") val memberInfo: List<MemberInfo>,
    @SerializedName("content") val content: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("like") val like: Int,
    @SerializedName("dislike") val dislike: Int
)

data class MemberInfo(
    @SerializedName("profile") val profile: String,
    @SerializedName("name") val name: String
)