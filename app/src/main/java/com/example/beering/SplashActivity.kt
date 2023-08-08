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
import com.example.beering.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadSplashScreen()

//        val videoView = binding.logoVideo
//        val splashLayout = binding.logoVideoSplash
//
//        val videoPath = "android.resource://" + packageName + "/" + R.raw.logo_video
//
//        val uri = Uri.parse(videoPath)
//        videoView.setVideoURI(uri)
//
//        // 비디오가 준비되면 스플래시 화면을 숨기고 VideoView를 표시.
//        videoView.setOnPreparedListener {
//            splashLayout.visibility = View.GONE
//            videoView.visibility = View.VISIBLE
//        }
//        videoView.start()

//        val imageView = binding.logoVideo
//        imageView.setImageResource(R.drawable.logo_test)
//        val animationDrawable = imageView.drawable as? AnimationDrawable
//        animationDrawable?.start()

    }

    fun loadSplashScreen(){
        var handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, LoginRequestActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)


    }

}