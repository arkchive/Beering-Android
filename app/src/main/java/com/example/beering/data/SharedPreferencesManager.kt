package com.example.beering.data

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

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

