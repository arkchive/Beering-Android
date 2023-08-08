package com.example.beering.api

data class MyResponse(
    val isSuccess : Boolean,
    val responseCode : Int,
    val responseMessage : String,
    val result : MyResult

)

data class MyResult(
    val username : String,
    val nickname : String,
    val url : String,

)

