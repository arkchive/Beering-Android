package com.example.beering

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    //test
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Beering)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_request)
    }
}