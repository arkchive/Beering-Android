package com.example.beering.util.token

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import com.example.beering.feature.auth.login.LoginActivity
import com.example.beering.util.getRefreshToken
import com.example.beering.util.getRetrofit_header
import com.example.beering.util.setToken
import retrofit2.Call
import retrofit2.Response

fun token(context: Context) {

    val tokenApi = getRetrofit_header(getRefreshToken(context).toString()).create(TokenApiService::class.java)
    val refreshToken = TokenRequest(getRefreshToken(context).toString())


    tokenApi.getToken(refreshToken).enqueue(object : retrofit2.Callback<TokenResponse>{
        override fun onResponse(
            call: Call<TokenResponse>,
            response: Response<TokenResponse>
        ) {
            val resp = response.body()
            if(resp!!.isSuccess){
                val userToken = resp!!.jwt
                setToken(context, userToken)
            } else {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("토큰 만료")
                builder.setMessage("재로그인 하시겠습니까?")
                builder.setPositiveButton("네") { dialog, which ->
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                }
                builder.setNegativeButton("아니오") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }
        }

        override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("요청 오류")
            builder.setMessage("서버에 요청을 실패하였습니다.")
            builder.setPositiveButton("네") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

    })





}