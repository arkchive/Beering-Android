package com.example.beering

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.beering.databinding.ActivityJoinBinding

class JoinActivity: AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 이용약관 내용 정해지면, 각 dialog에 맞는 내용 채워주는 코드 작성해야함.





    }
}