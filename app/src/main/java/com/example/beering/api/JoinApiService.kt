package com.example.beering.api

import com.example.beering.data.Member
import com.example.beering.data.MemberResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface JoinApiService {
    @POST("/auth/signup")
    fun signUp(@Body member: Member): Call<MemberResponse>

    @GET("/members/validate/username")
    fun checkUsernameValidate(@Query("username") username: String): Call<MemberResponse>

    @GET("/members/validate/nickname")
    fun checkNicknameValidate(@Query("nickname") nickname: String): Call<MemberResponse>




}