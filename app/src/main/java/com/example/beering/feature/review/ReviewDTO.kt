package com.example.beering.feature.review

// 리뷰 등록 폼
data class ReviewWritingFormResponse(
    val isSuccess : Boolean,
    val responseCode : Int,
    val responseMessage : String,
    val result: List<ReviewWritingForm>
    )

data class ReviewWritingForm(
    val reviewOptionId: Int,
    val name: String
)

// 리뷰 등록

data class ReviewWritingRequest(
    val content : String,
    val totalRating : Float,
    val selectedOptions : List<ReviewSelectedOptions>
)

data class ReviewSelectedOptions(
    val reviewOptionId : Int,
    val rating : Float
)
data class ReviewWritingResponse(
    val isSuccess : Boolean,
    val responseCode : Int,
    val responseMessage : String,
    val result : ReviewWritingResult
)

data class ReviewWritingResult(
    val id : Int,
    val content : String
)



