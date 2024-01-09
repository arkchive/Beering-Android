package com.example.beering.api

data class ReviewDetailResponse(
    val isSuccess : Boolean,
    val responseCode : Int,
    val reponseMessage : String,
    val result : ReviewDetailResult
)

data class ReviewDetailResult(
    val memberId : Int,
    val nickName : String,
    val profileImage : String,
    val reviewId : Int,
    val reivewImageUrls : List<String>,
    val content : String,
    val createAt : String,
    val totalRating : Float,
    val selectedOptions : List<ReviewDetailSelectedOption>,
    val like : Int,
    val dislike : Int,
    val isTabomed : String
)

data class ReviewDetailSelectedOption(
    val name : String,
    val rating : Float
)