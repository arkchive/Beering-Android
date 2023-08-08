package com.example.beering

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.beering.data.changeLogin
import com.example.beering.databinding.ActivityLoginRequestBinding

class LoginRequestActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginRequestBinding


    //test
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Beering)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeLogin(this, false)



        binding.loginRequestButtonJoinCl.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.loginRequestSkipTv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}