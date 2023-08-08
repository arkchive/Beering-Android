package com.example.beering.data

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.beering.api.Jwt

fun changeLogin(context: Context, state : Boolean){
    val spf = context.getSharedPreferences("login",  AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putBoolean("isLogin", state)
    editor.apply()
}

fun stateLogin(context: Context) : Boolean{
    val spf = context.getSharedPreferences("login",  AppCompatActivity.MODE_PRIVATE)

    return spf.getBoolean("isLogin", false)!!

}


fun setToken(context: Context, token: Jwt){
    val spf = context.getSharedPreferences("token",  AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("accessToken", token.accessToken)
    editor.putString("refreshToken", token.refreshToken)
    editor.apply()
}

fun getAccessToken(context: Context) : String?{
    val spf = context.getSharedPreferences("token",  AppCompatActivity.MODE_PRIVATE)

    return spf.getString("accessToken", "")

}

fun getRefreshToken(context: Context) : String? {
    val spf = context.getSharedPreferences("token",  AppCompatActivity.MODE_PRIVATE)

    return spf.getString("refreshToken", "")

}


