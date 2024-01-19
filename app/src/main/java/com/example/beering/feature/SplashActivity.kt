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
        installSplashScreen()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadSplashScreen()

        Glide.with(this)
            .load(R.drawable.logo_test)
            .into(binding.logoVideo)
    }

    fun loadSplashScreen(){
        var handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, LoginRequestActivity::class.java)
            startActivity(intent)
            finish()
        }, 4300)


    }

}