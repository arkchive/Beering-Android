package com.example.beering.feature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumptech.glide.Glide
import com.example.beering.feature.auth.LoginRequestActivity
import com.example.beering.R
import com.example.beering.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadSplashScreen()
    }

    fun loadSplashScreen(){
        // 일정 시간 지연 이후 실행하기 위한 코드
        var handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            // 일정 시간 지나면 로그인 요청 화면 이동
            val intent = Intent(this, LoginRequestActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000) // 2초


    }

}