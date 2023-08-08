package com.example.beering.api

import com.example.beering.data.Member
import com.example.beering.data.MemberResponse
import com.example.beering.data.MemberResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface JoinApiService {
    @POST("/auth/signup")
    suspend fun signUp(@Body member: Member): Response<MemberResponse>
}