package com.example.beering

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.beering.databinding.ActivityDrinkDetailReviewsBinding

class DrinkDetailReviewsActivity : AppCompatActivity() {
    lateinit var binding : ActivityDrinkDetailReviewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrinkDetailReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            finish()

            val fragmentManager: FragmentManager = supportFragmentManager
            fragmentManager.popBackStack()
        }
    }
}