package com.example.beering.api

data class HomeResponse(
    val isSuccess : Boolean,
    val responseCode : Int,
    val responseMessage : String,
    val result : HomeResult

)

data class HomeResult(
    val username : String,
    val nickname : String,
    val url : String,

)

