package com.example.beering.api

import com.google.gson.annotations.SerializedName

// 리뷰 등록
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


data class ReviewWritingRequest(
    val content : String,
    val totalRating : Float,
    val selectedOptions : List<ReviewSelectedOptions>
)

data class ReviewSelectedOptions(
    val reviewOptionId : Int,
    val rating : Float
)
