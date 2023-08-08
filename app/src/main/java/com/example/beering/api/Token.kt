package com.example.beering.api

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.beering.MainActivity
import com.example.beering.R
import com.example.beering.data.*
import retrofit2.Call
import retrofit2.Response

fun token(context: Context) : Int {

    val tokenApi = getRetrofit_token(getRefreshToken(context).toString()).create(TokenApiService::class.java)
    val refreshToken = TokenRequest(getRefreshToken(context).toString())
    var responseCode : Int = 0


    tokenApi.getToken(refreshToken).enqueue(object : retrofit2.Callback<TokenResponse>{
        override fun onResponse(
            call: Call<TokenResponse>,
            response: Response<TokenResponse>
        ) {
            val resp = response.body()
            if(resp!!.isSuccess){
                val userToken = resp!!.jwt
                setToken(context, userToken)
            }
            responseCode = resp.responseCode
        }

        override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
            Log.d("GETTOKEN/FAILURE", t.message.toString())
        }

    })


    return responseCode



}