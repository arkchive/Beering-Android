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

        binding.agree1Ib.setOnClickListener {
            val terms1Dialog  = TermsDialog()
            terms1Dialog.show(supportFragmentManager, "terms1Dialog")
        }
        binding.agree2Ib.setOnClickListener {
            val terms2Dialog  = TermsDialog()
            terms2Dialog.show(supportFragmentManager, "terms2Dialog")
        }
        binding.agree3Ib.setOnClickListener {
            val terms3Dialog  = TermsDialog()
            terms3Dialog.show(supportFragmentManager, "terms3Dialog")
        }

        binding.joinBackIv.setOnClickListener {
            finish()
        }

        binding.joinBackCl.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }




    }
}