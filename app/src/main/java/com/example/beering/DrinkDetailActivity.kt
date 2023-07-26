package com.example.beering

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beering.databinding.ActivityDrinkDetailBinding
import com.example.beering.databinding.ActivityJoinBinding
import com.example.naverwebtoon.data.DrinkCover
import com.example.naverwebtoon.data.DrinkDetail

class DrinkDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityDrinkDetailBinding

    var isInterest = false

    var reviewAdapter: ReviewAdapter? = null
    var reviewList = ArrayList<DrinkDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrinkDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDummyData()

        reviewAdapter = ReviewAdapter(reviewList)
        binding.reviewRv.adapter = reviewAdapter
        binding.reviewRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.buttonInterest.setOnClickListener {
            isInterest = !isInterest
            updateInterest(isInterest)
        }

    }

    private fun initDummyData() {
        reviewList = arrayListOf(
            DrinkDetail(
                "하이네켄",
                R.drawable.login_request_banner,
                "이 맥주는 정말 맛있고 아주 달콤합니다. 이 맥주는 놀랍게도 수입맥주가 아니라 !!!!! 우리나라 조선시대에 만들어진 맥주입니돳 블라블라",
                4.5f,
                100,
                4.7f
            ),
            DrinkDetail(
                "하이두켄",
                R.drawable.login_request_banner,
                "이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷 이 맥주는 아주ㅅ 씁니닷",
                4.5f,
                105,
                4.7f
            ),
            DrinkDetail(
                "하이세켄",
                R.drawable.login_request_banner,
                "이 맥주는 딱히 설명할 것이 없네유ㅠㅠ",
                4.5f,
                200,
                4.7f
            )
        )


    }

    fun updateInterest(state : Boolean) {
        if (state) { // 찜하기 on 상태
            binding.interestOn.visibility= View.VISIBLE
            binding.interestOff.visibility= View.GONE
        }else {
            binding.interestOn.visibility= View.GONE
            binding.interestOff.visibility= View.VISIBLE
        }
    }


}
