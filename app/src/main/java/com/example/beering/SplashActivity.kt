package com.example.beering

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.MediaController
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumptech.glide.Glide
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