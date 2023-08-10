package com.example.beering

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.beering.databinding.ActivitySearchFilterBinding

class SearchFilterActivity : AppCompatActivity() {
    lateinit var binding : ActivitySearchFilterBinding

    // 선택상태 저장 변수
    var isName = false
    var isReview = false
    var isLowPrice = false
    var isScore = false

    var isTypeBeer = false
    var isTypeWine = false
    var isTypeTraditional = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.filter1Name.setOnClickListener{
            isName = !isName
            updateFilterName(isName)
        }
        binding.filter1Review.setOnClickListener{
            isReview = !isReview
            updateFilterReview(isReview)
        }
        binding.filter1LowPrice.setOnClickListener{
            isLowPrice = !isLowPrice
            updateFilterLowPrice(isLowPrice)
        }
        binding.filter1Score.setOnClickListener{
            isScore = !isScore
            updateFilterScore(isScore)
        }

        binding.beerCl.setOnClickListener {
            isTypeBeer = !isTypeBeer
            updateTypeBeer(isTypeBeer)
        }

        binding.wineCl.setOnClickListener {
            isTypeWine = !isTypeWine
            updateTypeWine(isTypeWine)
        }

        binding.traditionalCl.setOnClickListener {
            isTypeTraditional = !isTypeTraditional
            updateTypeTraditional(isTypeTraditional)
        }

        binding.buttonClose.setOnClickListener{
            finish()

            val fragmentManager: FragmentManager = supportFragmentManager
            fragmentManager.popBackStack()
        }

        binding.buttonSearchCv.setOnClickListener{
            //선택한 필터 정보가 적용된 주류검색 페이지로 이동.

        }
    }





    fun updateFilterName (state: Boolean){
        if (state){
            binding.filter1NameOn.visibility = View.VISIBLE
            binding.filter1NameOff.visibility = View.GONE

            if(isReview) {
                isReview = !isReview
                binding.filter1ReviewOn.visibility = View.GONE
                binding.filter1ReviewOff.visibility = View.VISIBLE
            }
            if(isLowPrice) {
                isLowPrice = !isLowPrice
                binding.filter1LowPriceOn.visibility = View.GONE
                binding.filter1LowPriceOff.visibility = View.VISIBLE
            }
            if(isScore) {
                isTypeBeer = !isTypeBeer
                binding.filter1ScoreOn.visibility = View.GONE
                binding.filter1ScoreOff.visibility = View.VISIBLE
            }
        }else {
            binding.filter1NameOn.visibility = View.GONE
            binding.filter1NameOff.visibility = View.VISIBLE
        }
    }
    fun updateFilterReview (state: Boolean){
        if (state){
            binding.filter1ReviewOn.visibility = View.VISIBLE
            binding.filter1ReviewOff.visibility = View.GONE

            if(isName) {
                isName = !isName
                binding.filter1NameOn.visibility = View.GONE
                binding.filter1NameOff.visibility = View.VISIBLE
            }
            if(isLowPrice) {
                isLowPrice = !isLowPrice
                binding.filter1LowPriceOn.visibility = View.GONE
                binding.filter1LowPriceOff.visibility = View.VISIBLE
            }
            if(isScore) {
                isTypeBeer = !isTypeBeer
                binding.filter1ScoreOn.visibility = View.GONE
                binding.filter1ScoreOff.visibility = View.VISIBLE
            }
        }else {
            binding.filter1ReviewOn.visibility = View.GONE
            binding.filter1ReviewOff.visibility = View.VISIBLE
        }
    }
    fun updateFilterLowPrice (state: Boolean){
        if (state){
            binding.filter1LowPriceOn.visibility = View.VISIBLE
            binding.filter1LowPriceOff.visibility = View.GONE

            if(isName) {
                isName = !isName
                binding.filter1NameOn.visibility = View.GONE
                binding.filter1NameOff.visibility = View.VISIBLE
            }
            if(isReview) {
                isReview = !isReview
                binding.filter1ReviewOn.visibility = View.GONE
                binding.filter1ReviewOff.visibility = View.VISIBLE
            }
            if(isScore) {
                isTypeBeer = !isTypeBeer
                binding.filter1ScoreOn.visibility = View.GONE
                binding.filter1ScoreOff.visibility = View.VISIBLE
            }
        }else {
            binding.filter1LowPriceOn.visibility = View.GONE
            binding.filter1LowPriceOff.visibility = View.VISIBLE
        }
    }
    fun updateFilterScore (state: Boolean){
        if (state){
            binding.filter1ScoreOn.visibility = View.VISIBLE
            binding.filter1ScoreOff.visibility = View.GONE

            if(isName) {
                isName = !isName
                binding.filter1NameOn.visibility = View.GONE
                binding.filter1NameOff.visibility = View.VISIBLE
            }
            if(isReview) {
                isReview = !isReview
                binding.filter1ReviewOn.visibility = View.GONE
                binding.filter1ReviewOff.visibility = View.VISIBLE
            }
            if(isLowPrice) {
                isLowPrice = !isLowPrice
                binding.filter1LowPriceOn.visibility = View.GONE
                binding.filter1LowPriceOff.visibility = View.VISIBLE
            }
        }else {
            binding.filter1ScoreOn.visibility = View.GONE
            binding.filter1ScoreOff.visibility = View.VISIBLE
        }
    }


    fun updateTypeBeer (state : Boolean) {
        if (state) {
            binding.beerOnIv.visibility = View.VISIBLE
            binding.beerOffIv.visibility = View.GONE

        } else {
            binding.beerOffIv.visibility = View.VISIBLE
            binding.beerOnIv.visibility = View.GONE
        }
    }
    fun updateTypeWine (state : Boolean) {
        if (state) {
            binding.wineOnIv.visibility = View.VISIBLE
            binding.wineOffIv.visibility = View.GONE

        } else {
            binding.wineOffIv.visibility = View.VISIBLE
            binding.wineOnIv.visibility = View.GONE
        }
    }
    fun updateTypeTraditional (state : Boolean) {
        if (state) {
            binding.traditionalOnIv.visibility = View.VISIBLE
            binding.traditionalOffIv.visibility = View.GONE

        } else {
            binding.traditionalOffIv.visibility = View.VISIBLE
            binding.traditionalOnIv.visibility = View.GONE
        }
    }

}